package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.BadgeDao
import com.educalab.filosofar.data.local.dao.DailyQuestionDao
import com.educalab.filosofar.data.local.dao.DilemmaDao
import com.educalab.filosofar.data.local.dao.IslandDao
import com.educalab.filosofar.data.local.dao.LogicDao
import com.educalab.filosofar.data.local.dao.PerspectiveDao
import com.educalab.filosofar.data.local.dao.ProgressDao
import com.educalab.filosofar.data.local.dao.ReflectionDao
import com.educalab.filosofar.data.local.dao.SelfDebateDao
import com.educalab.filosofar.data.local.entity.IslandProgressEntity
import com.educalab.filosofar.data.local.entity.UserBadgeEntity
import com.educalab.filosofar.domain.logic.BadgeEngine
import com.educalab.filosofar.domain.logic.IslandRawCounts
import com.educalab.filosofar.domain.logic.ProgressCalculator
import com.educalab.filosofar.domain.model.Badge
import com.educalab.filosofar.domain.model.Island
import com.educalab.filosofar.domain.model.IslandProgress
import com.educalab.filosofar.domain.model.ModuleStatus
import com.educalab.filosofar.domain.model.ProfileStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Repositorio central de progreso. Cada vez que se guarda un intento en
 * cualquier módulo (pregunta, dilema, lógica, perspectiva, debate), se debe
 * llamar a [recalculateAll] para que el mapa y las insignias reflejen la
 * realidad. Nunca se incrementa un contador manualmente: todo se deriva de
 * las tablas de intentos.
 */
class ProgressRepository(
    private val islandDao: IslandDao,
    private val progressDao: ProgressDao,
    private val questionDao: DailyQuestionDao,
    private val dilemmaDao: DilemmaDao,
    private val logicDao: LogicDao,
    private val perspectiveDao: PerspectiveDao,
    private val debateDao: SelfDebateDao,
    private val reflectionDao: ReflectionDao,
    private val badgeDao: BadgeDao
) {

    fun observeIslands(): Flow<List<Island>> = islandDao.observeAll().map { list ->
        list.map { Island(it.id, it.name, it.tagline, it.sortOrder, it.themeColorHex, it.iconKey, it.unlockRequiredCrystals) }
    }

    fun observeProgress(): Flow<List<IslandProgress>> = progressDao.observeAll().map { list ->
        list.map {
            IslandProgress(
                islandId = it.islandId,
                crystalsEarned = it.crystalsEarned,
                crystalsTotal = it.crystalsTotal,
                questionsAnswered = it.questionsAnswered,
                questionsTotal = it.questionsTotal,
                dilemmasCompleted = it.dilemmasCompleted,
                dilemmasTotal = it.dilemmasTotal,
                logicSolved = it.logicSolved,
                logicTotal = it.logicTotal,
                perspectivesCompleted = it.perspectivesCompleted,
                perspectivesTotal = it.perspectivesTotal,
                debatesCompleted = it.debatesCompleted,
                debatesTotal = it.debatesTotal,
                status = ModuleStatus.valueOf(it.status)
            )
        }
    }

    fun observeIslandsWithProgress(): Flow<List<Pair<Island, IslandProgress>>> =
        combine(observeIslands(), observeProgress()) { islands, progress ->
            val progressById = progress.associateBy { it.islandId }
            islands.mapNotNull { island -> progressById[island.id]?.let { island to it } }
        }

    fun observeBadges(): Flow<List<Badge>> = combine(badgeDao.observeAll(), badgeDao.observeUnlocked()) { badges, unlocked ->
        val unlockedMap = unlocked.associateBy { it.badgeId }
        badges.map { b ->
            val u = unlockedMap[b.id]
            Badge(b.id, b.name, b.description, b.iconKey, b.unlockCriteriaKey, u != null, u?.unlockedAtEpochMs)
        }
    }

    fun observeTotalCrystals(): Flow<Int> = progressDao.observeTotalCrystals()

    /**
     * Recalcula progreso de todas las islas y evalúa insignias. Llamar tras
     * cada intento nuevo. Las islas se desbloquean en orden: la primera
     * siempre está disponible, y cada isla siguiente se desbloquea solo
     * cuando la anterior completa todas las misiones de su día 1 (la
     * pregunta del día, 5 dilemas, 5 ejercicios de perspectiva, los 3 retos
     * de lógica del día 1 y el primer debate).
     */
    suspend fun recalculateAll() {
        val allIslands = islandDaoSnapshot()
        val computedList = mutableListOf<IslandProgressEntity>()

        val perIslandRaw = allIslands.associateWith { island ->
            val questionsTotal = countQuestionsTotal(island.id)
            val questionsAnswered = questionDao.countAnsweredInIsland(island.id)
            val dilemmasTotal = countDilemmasTotal(island.id)
            val dilemmasCompleted = dilemmaDao.countCompletedInIsland(island.id)
            val logicTotal = countLogicTotal(island.id)
            val logicSolved = logicDao.countSolvedInIsland(island.id)
            val perspectivesTotal = countPerspectivesTotal(island.id)
            val perspectivesCompleted = perspectiveDao.countCompletedInIsland(island.id)
            val debatesTotal = countDebatesTotal(island.id)
            val debatesCompleted = debateDao.countCompletedInIsland(island.id)
            IslandRawCounts(
                islandId = island.id,
                questionsAnswered = questionsAnswered,
                questionsTotal = questionsTotal,
                dilemmasCompleted = dilemmasCompleted,
                dilemmasTotal = dilemmasTotal,
                logicSolved = logicSolved,
                logicTotal = logicTotal,
                perspectivesCompleted = perspectivesCompleted,
                perspectivesTotal = perspectivesTotal,
                debatesCompleted = debatesCompleted,
                debatesTotal = debatesTotal,
                hasOpinionRevisionInIsland = true // ver nota MASTERED en docs; se evalúa de forma simplificada
            )
        }

        val now = System.currentTimeMillis()
        var previousIslandDay1Complete = true // la primera isla siempre está disponible
        allIslands.sortedBy { it.sortOrder }.forEach { island ->
            val raw = perIslandRaw.getValue(island)
            val computed = ProgressCalculator.compute(raw, unlocked = previousIslandDay1Complete)
            computedList += IslandProgressEntity(
                islandId = island.id,
                crystalsEarned = computed.crystalsEarned,
                crystalsTotal = computed.crystalsTotal,
                questionsAnswered = raw.questionsAnswered,
                questionsTotal = raw.questionsTotal,
                dilemmasCompleted = raw.dilemmasCompleted,
                dilemmasTotal = raw.dilemmasTotal,
                logicSolved = raw.logicSolved,
                logicTotal = raw.logicTotal,
                perspectivesCompleted = raw.perspectivesCompleted,
                perspectivesTotal = raw.perspectivesTotal,
                debatesCompleted = raw.debatesCompleted,
                debatesTotal = raw.debatesTotal,
                status = computed.status.name,
                lastUpdatedEpochMs = now
            )
            previousIslandDay1Complete = computed.day1Complete
        }
        progressDao.upsertAll(computedList)

        evaluateAndUnlockBadges(computedList)
    }

    private suspend fun evaluateAndUnlockBadges(progress: List<IslandProgressEntity>) {
        val stats = ProfileStats(
            questionsAnswered = countAllQuestionAttempts(),
            dilemmasExplored = countAllDilemmaAttempts(),
            dualPerspectivesViewed = dilemmaDao.countPerspectivesViewedInDilemmas() + perspectiveDao.countRevealedOtherRole(),
            logicChallengesSolved = countAllLogicSolved(),
            debatesCompleted = countAllDebateAttempts(),
            opinionChanges = reflectionDao.countOpinionsChanged(),
            journalEntries = countAllJournalEntries(),
            islandsWithProgress = progress.count { it.crystalsEarned > 0 }
        )

        val met = BadgeEngine.evaluateGlobalCriteria(stats)
        val alreadyUnlocked = badgeDao.unlockedIds().toSet()
        val now = System.currentTimeMillis()

        met.filter { it !in alreadyUnlocked }.forEach { criteriaKey ->
            val badge = allBadgesSnapshot().firstOrNull { it.unlockCriteriaKey == criteriaKey }
            if (badge != null) badgeDao.unlock(UserBadgeEntity(badge.id, now))
        }

        progress.filter { it.status == "COMPLETED" || it.status == "MASTERED" }.forEach { p ->
            val key = BadgeEngine.evaluateIslandCompletion(p.islandId, true)
            if (key != null && key !in alreadyUnlocked) {
                val badge = allBadgesSnapshot().firstOrNull { it.unlockCriteriaKey == key }
                if (badge != null) badgeDao.unlock(UserBadgeEntity(badge.id, now))
            }
        }
    }

    // --- Helpers de snapshot (una lectura puntual, no reactiva) ---
    private suspend fun islandDaoSnapshot() = islandDao.observeAll().let { flow ->
        var result: List<com.educalab.filosofar.data.local.entity.PhilosophyIslandEntity> = emptyList()
        flow.first().let { result = it }
        result
    }

    private suspend fun allBadgesSnapshot() = badgeDao.observeAll().first()

    private suspend fun countQuestionsTotal(islandId: String) =
        questionDao.observeByIsland(islandId).first().size

    private suspend fun countDilemmasTotal(islandId: String) =
        dilemmaDao.observeByIsland(islandId).first().size

    private suspend fun countLogicTotal(islandId: String) =
        logicDao.observeByIsland(islandId).first().size

    private suspend fun countPerspectivesTotal(islandId: String) =
        perspectiveDao.observeByIsland(islandId).first().size

    private suspend fun countDebatesTotal(islandId: String) =
        debateDao.observeByIsland(islandId).first().size

    private suspend fun countAllQuestionAttempts() =
        questionDao.observeAllAttempts().first().map { it.questionId }.distinct().size

    private suspend fun countAllDilemmaAttempts() =
        dilemmaDao.observeAllAttempts().first().map { it.dilemmaId }.distinct().size

    private suspend fun countAllLogicSolved() =
        logicDao.observeAllAttempts().first().count { it.wasCorrect }

    private suspend fun countAllDebateAttempts() =
        debateDao.observeAllAttempts().first().map { it.debateId }.distinct().size

    private suspend fun countAllJournalEntries() =
        reflectionDao.observeAll().first().size
}
