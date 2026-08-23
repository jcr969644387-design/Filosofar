package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.PerspectiveDao
import com.educalab.filosofar.data.local.dao.ReasonCardDao
import com.educalab.filosofar.data.local.entity.PerspectiveAttemptEntity
import com.educalab.filosofar.domain.model.PerspectiveExercise
import com.educalab.filosofar.domain.model.ReasonCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Estado de desbloqueo diario de "Otro punto de vista" para una isla. */
data class PerspectiveUnlockState(val exercises: List<PerspectiveExercise>, val unlockedCount: Int)

class ReasonCardRepository(private val dao: ReasonCardDao) {
    fun observeAll(): Flow<List<ReasonCard>> = dao.observeAll().map { list -> list.map { it.toDomain() } }

    suspend fun randomSample(count: Int): List<ReasonCard> = dao.randomSample(count).map { it.toDomain() }

    private fun com.educalab.filosofar.data.local.entity.ReasonCardEntity.toDomain() =
        ReasonCard(id, text, valueTags.split(",").filter { it.isNotBlank() }, iconKey)
}

class PerspectiveRepository(
    private val dao: PerspectiveDao,
    private val progressRepository: ProgressRepository,
    private val dailyQuestionRepository: DailyQuestionRepository
) {
    fun observeByIsland(islandId: String): Flow<List<PerspectiveExercise>> =
        dao.observeByIsland(islandId).map { list -> list.map { it.toDomain() } }

    /**
     * Calcula cuántos ejercicios de la isla están desbloqueados: se
     * desbloquean 5 nuevos por cada pregunta del día que ya se respondió en
     * esa isla.
     */
    suspend fun getIslandUnlockState(islandId: String): PerspectiveUnlockState {
        val ordered = dao.listByIslandOnce(islandId).map { it.toDomain() }
        if (ordered.isEmpty()) return PerspectiveUnlockState(emptyList(), 0)

        val answered = dailyQuestionRepository.countAnsweredInIsland(islandId)
        val unlockedCount = (answered * ITEMS_PER_DAY).coerceAtMost(ordered.size)
        return PerspectiveUnlockState(ordered, unlockedCount)
    }

    fun observeCompletedIds(islandId: String): Flow<Set<String>> =
        dao.observeCompletedInIsland(islandId).map { it.toSet() }

    suspend fun getFull(id: String): PerspectiveExercise? = dao.get(id)?.toDomain()

    suspend fun latestAttempt(exerciseId: String): PerspectiveAttemptEntity? = dao.latestAttemptFor(exerciseId)

    suspend fun recordAttempt(exerciseId: String, choseRole: String, revealedOtherRole: Boolean, reflectionAnswer: String) {
        dao.insertAttempt(
            PerspectiveAttemptEntity(
                exerciseId = exerciseId,
                choseRole = choseRole,
                revealedOtherRole = revealedOtherRole,
                reflectionAnswer = reflectionAnswer.trim(),
                attemptedAtEpochMs = System.currentTimeMillis()
            )
        )
        progressRepository.recalculateAll()
    }

    private fun com.educalab.filosofar.data.local.entity.PerspectiveExerciseEntity.toDomain() =
        PerspectiveExercise(id, islandId, situation, roleAText, roleAViewpoint, roleBText, roleBViewpoint, reflectionPrompt, orderInIsland)

    companion object {
        private const val ITEMS_PER_DAY = 5
    }
}
