package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.DilemmaDao
import com.educalab.filosofar.data.local.entity.DilemmaAttemptEntity
import com.educalab.filosofar.domain.model.Dilemma
import com.educalab.filosofar.domain.model.DilemmaOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DilemmaRepository(
    private val dao: DilemmaDao,
    private val progressRepository: ProgressRepository
) {
    fun observeByIsland(islandId: String): Flow<List<Dilemma>> =
        dao.observeByIsland(islandId).map { list -> list.map { entity -> entity.toDomain(emptyList()) } }

    suspend fun latestAttempt(dilemmaId: String): DilemmaAttemptEntity? = dao.latestAttemptFor(dilemmaId)

    suspend fun getFull(dilemmaId: String): Dilemma? {
        val entity = dao.get(dilemmaId) ?: return null
        val options = dao.optionsFor(dilemmaId).map {
            DilemmaOption(it.id, it.label, it.consequence, it.lumiView, it.noxView)
        }
        return entity.toDomain(options)
    }

    suspend fun recordAttempt(dilemmaId: String, chosenOptionId: String, viewedAlternativePerspective: Boolean) {
        dao.insertAttempt(
            DilemmaAttemptEntity(
                dilemmaId = dilemmaId,
                chosenOptionId = chosenOptionId,
                viewedAlternativePerspective = viewedAlternativePerspective,
                attemptedAtEpochMs = System.currentTimeMillis()
            )
        )
        progressRepository.recalculateAll()
    }

    private fun com.educalab.filosofar.data.local.entity.DilemmaEntity.toDomain(options: List<DilemmaOption>) =
        Dilemma(id, islandId, title, scenario, options)
}
