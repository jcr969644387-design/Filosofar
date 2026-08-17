package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.PerspectiveDao
import com.educalab.filosofar.data.local.dao.ReasonCardDao
import com.educalab.filosofar.data.local.entity.PerspectiveAttemptEntity
import com.educalab.filosofar.domain.model.PerspectiveExercise
import com.educalab.filosofar.domain.model.ReasonCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReasonCardRepository(private val dao: ReasonCardDao) {
    fun observeAll(): Flow<List<ReasonCard>> = dao.observeAll().map { list -> list.map { it.toDomain() } }

    suspend fun randomSample(count: Int): List<ReasonCard> = dao.randomSample(count).map { it.toDomain() }

    private fun com.educalab.filosofar.data.local.entity.ReasonCardEntity.toDomain() =
        ReasonCard(id, text, valueTags.split(",").filter { it.isNotBlank() }, iconKey)
}

class PerspectiveRepository(
    private val dao: PerspectiveDao,
    private val progressRepository: ProgressRepository
) {
    fun observeByIsland(islandId: String): Flow<List<PerspectiveExercise>> =
        dao.observeByIsland(islandId).map { list -> list.map { it.toDomain() } }

    suspend fun getFull(id: String): PerspectiveExercise? = dao.get(id)?.toDomain()

    suspend fun recordAttempt(exerciseId: String, choseRole: String, revealedOtherRole: Boolean) {
        dao.insertAttempt(
            PerspectiveAttemptEntity(
                exerciseId = exerciseId,
                choseRole = choseRole,
                revealedOtherRole = revealedOtherRole,
                attemptedAtEpochMs = System.currentTimeMillis()
            )
        )
        progressRepository.recalculateAll()
    }

    private fun com.educalab.filosofar.data.local.entity.PerspectiveExerciseEntity.toDomain() =
        PerspectiveExercise(id, islandId, situation, roleAText, roleAViewpoint, roleBText, roleBViewpoint, reflectionPrompt)
}
