package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.SelfDebateDao
import com.educalab.filosofar.data.local.entity.SelfDebateAttemptEntity
import com.educalab.filosofar.domain.model.DebateArgument
import com.educalab.filosofar.domain.model.SelfDebate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SelfDebateRepository(
    private val dao: SelfDebateDao,
    private val progressRepository: ProgressRepository
) {
    fun observeByIsland(islandId: String): Flow<List<SelfDebate>> =
        dao.observeByIsland(islandId).map { list -> list.map { it.toDomain(emptyList()) } }

    suspend fun getFull(debateId: String): SelfDebate? {
        val entity = dao.get(debateId) ?: return null
        val args = dao.argumentsFor(debateId).map { DebateArgument(it.id, it.text, it.correctSide) }
        return entity.toDomain(args.shuffled())
    }

    suspend fun recordAttempt(debateId: String, correctlyPlacedCount: Int, totalArguments: Int, personalConclusion: String) {
        dao.insertAttempt(
            SelfDebateAttemptEntity(
                debateId = debateId,
                correctlyPlacedCount = correctlyPlacedCount,
                totalArguments = totalArguments,
                personalConclusion = personalConclusion.trim(),
                attemptedAtEpochMs = System.currentTimeMillis()
            )
        )
        progressRepository.recalculateAll()
    }

    private fun com.educalab.filosofar.data.local.entity.SelfDebateEntity.toDomain(args: List<DebateArgument>) =
        SelfDebate(id, islandId, topic, sideALabel, sideBLabel, args)
}
