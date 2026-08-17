package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.DailyQuestionDao
import com.educalab.filosofar.data.local.entity.QuestionAttemptEntity
import com.educalab.filosofar.domain.model.DailyQuestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DailyQuestionRepository(
    private val dao: DailyQuestionDao,
    private val progressRepository: ProgressRepository
) {
    fun observeByIsland(islandId: String): Flow<List<DailyQuestion>> =
        dao.observeByIsland(islandId).map { list -> list.map { it.toDomain() } }

    /** Pregunta sugerida para "Pregunta del día": prioriza una que nunca se respondió. */
    suspend fun pickQuestionOfTheDay(): DailyQuestion? {
        val q = dao.pickUnanswered() ?: dao.pickAny()
        return q?.toDomain()
    }

    suspend fun pickQuestionForRevision(): DailyQuestion? = dao.pickAlreadyAnsweredForRevision()?.toDomain()

    suspend fun lastAnswerFor(questionId: String): String? =
        dao.observeAttemptsFor(questionId).first().firstOrNull()?.answerText

    suspend fun submitAnswer(questionId: String, answerText: String) {
        val trimmed = answerText.trim()
        dao.insertAttempt(
            QuestionAttemptEntity(
                questionId = questionId,
                answerText = trimmed,
                wordCount = if (trimmed.isEmpty()) 0 else trimmed.split(Regex("\\s+")).size,
                answeredAtEpochMs = System.currentTimeMillis()
            )
        )
        progressRepository.recalculateAll()
    }

    private fun com.educalab.filosofar.data.local.entity.DailyQuestionEntity.toDomain() =
        DailyQuestion(id, islandId, text, hint)
}
