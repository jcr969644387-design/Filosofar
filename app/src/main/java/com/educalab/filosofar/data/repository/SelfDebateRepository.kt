package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.SelfDebateDao
import com.educalab.filosofar.data.local.entity.SelfDebateAttemptEntity
import com.educalab.filosofar.domain.model.DebateArgument
import com.educalab.filosofar.domain.model.SelfDebate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Estado de desbloqueo diario de debates para una isla. */
data class SelfDebateUnlockState(val debates: List<SelfDebate>, val unlockedCount: Int)

class SelfDebateRepository(
    private val dao: SelfDebateDao,
    private val progressRepository: ProgressRepository,
    private val dailyQuestionRepository: DailyQuestionRepository
) {
    fun observeByIsland(islandId: String): Flow<List<SelfDebate>> =
        dao.observeByIsland(islandId).map { list -> list.map { it.toDomain(emptyList()) } }

    /** Calcula cuántos debates de la isla están desbloqueados: 1 nuevo por cada pregunta del día respondida. */
    suspend fun getIslandUnlockState(islandId: String): SelfDebateUnlockState {
        val ordered = dao.listByIslandOnce(islandId).map { it.toDomain(emptyList()) }
        if (ordered.isEmpty()) return SelfDebateUnlockState(emptyList(), 0)

        val answered = dailyQuestionRepository.countAnsweredInIsland(islandId)
        val unlockedCount = (answered * ITEMS_PER_DAY).coerceAtMost(ordered.size)
        return SelfDebateUnlockState(ordered, unlockedCount)
    }

    fun observeCompletedIds(islandId: String): Flow<Set<String>> =
        dao.observeCompletedInIsland(islandId).map { it.toSet() }

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

    companion object {
        private const val ITEMS_PER_DAY = 1
    }
}
