package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.LogicDao
import com.educalab.filosofar.data.local.entity.LogicAttemptEntity
import com.educalab.filosofar.domain.model.LogicChallenge
import com.educalab.filosofar.domain.model.LogicChallengeType
import com.educalab.filosofar.domain.model.LogicItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Estado de desbloqueo diario de retos de lógica para una isla. */
data class LogicUnlockState(val challenges: List<LogicChallenge>, val unlockedCount: Int)

class LogicRepository(
    private val dao: LogicDao,
    private val progressRepository: ProgressRepository,
    private val dailyQuestionRepository: DailyQuestionRepository
) {
    fun observeByIsland(islandId: String): Flow<List<LogicChallenge>> =
        dao.observeByIsland(islandId).map { list -> list.map { it.toDomain(emptyList()) } }

    /**
     * Calcula cuántos retos de la isla están desbloqueados: se desbloquean 3
     * nuevos (uno de cada tipo) por cada pregunta del día ya respondida.
     */
    suspend fun getIslandUnlockState(islandId: String): LogicUnlockState {
        val ordered = dao.listByIslandOnce(islandId).map { it.toDomain(emptyList()) }
        if (ordered.isEmpty()) return LogicUnlockState(emptyList(), 0)

        val answered = dailyQuestionRepository.countAnsweredInIsland(islandId)
        val unlockedCount = (answered * ITEMS_PER_DAY).coerceAtMost(ordered.size)
        return LogicUnlockState(ordered, unlockedCount)
    }

    fun observeCompletedIds(islandId: String): Flow<Set<String>> =
        dao.observeCompletedInIsland(islandId).map { it.toSet() }

    suspend fun wasSolved(challengeId: String): Boolean = dao.latestCorrectAttemptFor(challengeId) != null

    suspend fun getFull(challengeId: String): LogicChallenge? {
        val entity = dao.get(challengeId) ?: return null
        val items = dao.itemsFor(challengeId).map {
            LogicItem(it.id, it.text, it.correctPosition, it.pairKey, it.role, it.isFlawed)
        }
        return entity.toDomain(items)
    }

    suspend fun recordAttempt(challengeId: String, wasCorrect: Boolean, attemptsUsed: Int) {
        dao.insertAttempt(
            LogicAttemptEntity(
                challengeId = challengeId,
                wasCorrect = wasCorrect,
                attemptsUsed = attemptsUsed,
                attemptedAtEpochMs = System.currentTimeMillis()
            )
        )
        progressRepository.recalculateAll()
    }

    suspend fun challengesForReview(limit: Int = 5): List<LogicChallenge> {
        val ids = dao.recentFailedChallengeIds(limit)
        return ids.mapNotNull { getFull(it) }
    }

    private fun com.educalab.filosofar.data.local.entity.LogicChallengeEntity.toDomain(items: List<LogicItem>) =
        LogicChallenge(id, islandId, LogicChallengeType.valueOf(type), prompt, explanation, items)

    companion object {
        private const val ITEMS_PER_DAY = 3
    }
}
