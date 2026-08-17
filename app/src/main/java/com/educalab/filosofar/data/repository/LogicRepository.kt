package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.LogicDao
import com.educalab.filosofar.data.local.entity.LogicAttemptEntity
import com.educalab.filosofar.domain.model.LogicChallenge
import com.educalab.filosofar.domain.model.LogicChallengeType
import com.educalab.filosofar.domain.model.LogicItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LogicRepository(
    private val dao: LogicDao,
    private val progressRepository: ProgressRepository
) {
    fun observeByIsland(islandId: String): Flow<List<LogicChallenge>> =
        dao.observeByIsland(islandId).map { list -> list.map { it.toDomain(emptyList()) } }

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
}
