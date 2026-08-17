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
import kotlinx.coroutines.flow.map

/**
 * Repositorio central de progreso. Cada vez que se guarda un intento en
 * cualquier módulo (pregunta, dilema, lógica, perspectiva), se debe llamar a
 * [recalculateAll] para que el mapa y las insignias reflejen la realidad.
 * Nunca se incrementa un contador manualmente: todo se deriva de las tablas
 * de intentos.
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

    /** Recalcula progreso de todas las islas y evalúa insignias. Llamar tras cada intento nuevo. */
    suspend fun recalculateAll() {
        val islands = islandDao.observeAll()
        // Se lee una vez de forma directa vía DAO síncrono auxiliar para evitar acoplar a Flow aquí:
        val allIslands = islandDaoSnapshot()
        var globalCrystals = 0
        val computedList = mutableListOf<IslandProgressEntity>()

        // Primera pasada: crystalsEarned por isla (no depende de orden de desbloqueo)
        val perIslandRaw = allIslands.associateWith { island ->
            val questionsTotal = countQuestionsTotal(island.id)
            val questionsAnswered = questionDao.countAnsweredInIsland(island.id)
            val dilemmasTotal = countDilemmasTotal(island.id)
            val dilemmasCompleted = dilemmaDao.countCompletedInIsland(island.id)
            val logicTotal = countLogicTotal(island.id)
            val logicSolved = logicDao.countSolvedInIsland(island.id)
            val perspectivesTotal = countPerspectivesTotal(island.id)
            val perspectivesCompleted = perspectiveDao.countCompletedInIsland(island.id)
            IslandRawCounts(
                islandId = island.id,
                unlockRequiredCrystals = island.unlockRequiredCrystals,
                questionsAnswered = questionsAnswered,
                questionsTotal = questionsTotal,
                dilemmasCompleted = dilemmasCompleted,
                dilemmasTotal = dilemmasTotal,
                logicSolved = logicSolved,
                logicTotal = logicTotal,
                perspectivesCompleted = perspectivesCompleted,
                perspectivesTotal = perspectivesTotal,
                hasOpinionRevisionInIsland = true // ver nota MASTERED en docs; se evalúa de forma simplificada
            )
        }

        globalCrystals = perIslandRaw.values.sumOf { it.questionsAnswered + it.dilemmasCompleted + it.logicSolved + it.perspectivesCompleted }

        val now = System.currentTimeMillis()
        allIslands.sortedBy { it.sortOrder }.forEach { island ->
            val raw = perIslandRaw.getValue(island)
            val computed = ProgressCalculator.compute(raw, globalCrystals)
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
                status = computed.status.name,
                lastUpdatedEpochMs = now
            )
        }
        progressDao.upsertAll(computedList)

        evaluateAndUnlockBadges(computedList)
    }

    private suspend fun evaluateAndUnlockBadges(progress: List<IslandProgressEntity>) {
        val stats = ProfileStats(
            questionsAnswered = questionDao.observeAllAttempts().let { 0 } // placeholder overwritten below
                .let { countAllQuestionAttempts() },
            dilemmasExplored = countAllDilemmaAttempts(),
            dualPerspectivesViewed = dilemmaDao.countPerspectivesViewedInDilemmas() + perspectiveDao.countRevealedOtherRole(),
            logicChallengesSolved = countAllLogicSolved(),
            debatesCompleted = countAllDebateAttempts(),
            opinionChanges = reflectionDao.countOpinionsChanged(),
            journalEntries = countAllJournalEntries(),
            islandsWithProgress = progress.count { it.crystalsEarned > 0 }
        )

        val met = com.educalab.filosofar.domain.logic.BadgeEngine.evaluateGlobalCriteria(stats)
        val alreadyUnlocked = badgeDao.unlockedIds().toSet()
        val now = System.currentTimeMillis()

        met.filter { it !in alreadyUnlocked }.forEach { criteriaKey ->
            val badge = allBadgesSnapshot().firstOrNull { it.unlockCriteriaKey == criteriaKey }
            if (badge != null) badgeDao.unlock(UserBadgeEntity(badge.id, now))
        }

        progress.filter { it.status == "COMPLETED" || it.status == "MASTERED" }.forEach { p ->
            val key = com.educalab.filosofar.domain.logic.BadgeEngine.evaluateIslandCompletion(p.islandId, true)
            if (key != null && key !in alreadyUnlocked) {
                val badge = allBadgesSnapshot().firstOrNull { it.unlockCriteriaKey == key }
                if (badge != null) badgeDao.unlock(UserBadgeEntity(badge.id, now))
            }
        }
    }

    // --- Helpers de snapshot (una lectura puntual, no reactiva) ---
    private suspend fun islandDaoSnapshot() = islandDao.observeAll().let { flow ->
        var result: List<com.educalab.filosofar.data.local.entity.PhilosophyIslandEntity> = emptyList()
        kotlinx.coroutines.flow.first(flow).let { result = it }
        result
    }

    private suspend fun allBadgesSnapshot() = kotlinx.coroutines.flow.first(badgeDao.observeAll())

    private suspend fun countQuestionsTotal(islandId: String) =
        kotlinx.coroutines.flow.first(questionDao.observeByIsland(islandId)).size

    private suspend fun countDilemmasTotal(islandId: String) =
        kotlinx.coroutines.flow.first(dilemmaDao.observeByIsland(islandId)).size

    private suspend fun countLogicTotal(islandId: String) =
        kotlinx.coroutines.flow.first(logicDao.observeByIsland(islandId)).size

    private suspend fun countPerspectivesTotal(islandId: String) =
        kotlinx.coroutines.flow.first(perspectiveDao.observeByIsland(islandId)).size

    private suspend fun countAllQuestionAttempts() =
        kotlinx.coroutines.flow.first(questionDao.observeAllAttempts()).map { it.questionId }.distinct().size

    private suspend fun countAllDilemmaAttempts() =
        kotlinx.coroutines.flow.first(dilemmaDao.observeAllAttempts()).map { it.dilemmaId }.distinct().size

    private suspend fun countAllLogicSolved() =
        kotlinx.coroutines.flow.first(logicDao.observeAllAttempts()).count { it.wasCorrect }

    private suspend fun countAllDebateAttempts() =
        kotlinx.coroutines.flow.first(debateDao.observeAllAttempts()).map { it.debateId }.distinct().size

    private suspend fun countAllJournalEntries() =
        kotlinx.coroutines.flow.first(reflectionDao.observeAll()).size
}
