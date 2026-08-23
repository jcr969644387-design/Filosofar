package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.DilemmaDao
import com.educalab.filosofar.data.local.entity.DilemmaAttemptEntity
import com.educalab.filosofar.domain.model.Dilemma
import com.educalab.filosofar.domain.model.DilemmaOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Estado de desbloqueo diario de dilemas para una isla. */
data class DilemmaUnlockState(val dilemmas: List<Dilemma>, val unlockedCount: Int)

class DilemmaRepository(
    private val dao: DilemmaDao,
    private val progressRepository: ProgressRepository,
    private val dailyQuestionRepository: DailyQuestionRepository
) {
    fun observeByIsland(islandId: String): Flow<List<Dilemma>> =
        dao.observeByIsland(islandId).map { list -> list.map { entity -> entity.toDomain(emptyList()) } }

    /**
     * Calcula cuántos dilemas de la isla están desbloqueados: se desbloquean
     * 5 nuevos por cada pregunta del día que ya se respondió en esa isla.
     */
    suspend fun getIslandUnlockState(islandId: String): DilemmaUnlockState {
        val ordered = dao.listByIslandOnce(islandId).map { it.toDomain(emptyList()) }
        if (ordered.isEmpty()) return DilemmaUnlockState(emptyList(), 0)

        val answered = dailyQuestionRepository.countAnsweredInIsland(islandId)
        val unlockedCount = (answered * ITEMS_PER_DAY).coerceAtMost(ordered.size)
        return DilemmaUnlockState(ordered, unlockedCount)
    }

    fun observeCompletedIds(islandId: String): Flow<Set<String>> =
        dao.observeCompletedInIsland(islandId).map { it.toSet() }

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

    companion object {
        private const val ITEMS_PER_DAY = 5
    }
}
