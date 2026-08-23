package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.DailyQuestionDao
import com.educalab.filosofar.data.local.entity.DailyQuestionUnlockEntity
import com.educalab.filosofar.data.local.entity.QuestionAttemptEntity
import com.educalab.filosofar.domain.logic.DayGating
import com.educalab.filosofar.domain.model.DailyQuestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/** Estado de la Pregunta del día para una isla, en el momento actual. */
sealed class DailyQuestionDayState {
    data class ReadyToAnswer(val question: DailyQuestion, val dayNumber: Int, val totalDays: Int) : DailyQuestionDayState()
    data class AlreadyAnsweredToday(val question: DailyQuestion, val answerText: String, val dayNumber: Int, val totalDays: Int) : DailyQuestionDayState()
    data object AllCompleted : DailyQuestionDayState()
    data object NoQuestions : DailyQuestionDayState()
}

class DailyQuestionRepository(
    private val dao: DailyQuestionDao,
    private val progressRepository: ProgressRepository
) {
    fun observeByIsland(islandId: String): Flow<List<DailyQuestion>> =
        dao.observeByIsland(islandId).map { list -> list.map { it.toDomain() } }

    /** Cuántas preguntas del día ya se respondieron en esta isla: motor del desbloqueo del resto de módulos. */
    suspend fun countAnsweredInIsland(islandId: String): Int = dao.countAnsweredInIsland(islandId)

    /**
     * Calcula qué pregunta corresponde mostrar hoy en una isla: se desbloquea
     * una pregunta nueva cada día a partir de las 6:00 am, hasta agotar las
     * preguntas de la isla. Si la pregunta de hoy ya fue respondida, se
     * devuelve para mostrarla en modo lectura junto con la respuesta guardada.
     */
    suspend fun getTodayStatus(islandId: String): DailyQuestionDayState {
        val ordered = dao.listByIslandOnce(islandId)
        if (ordered.isEmpty()) return DailyQuestionDayState.NoQuestions

        val todayKey = DayGating.logicalDayKey()
        val anchor = dao.getUnlockAnchor(islandId) ?: DailyQuestionUnlockEntity(islandId, todayKey).also {
            dao.insertUnlockAnchor(it)
        }

        val dayIndex = (todayKey - anchor.startDayKey).toInt().coerceIn(0, ordered.size - 1)
        val isLastDay = dayIndex == ordered.size - 1
        val current = ordered[dayIndex]
        val lastAttempt = dao.latestAttemptFor(current.id)
        val dayNumber = dayIndex + 1

        return when {
            lastAttempt != null && isLastDay -> DailyQuestionDayState.AllCompleted
            lastAttempt != null -> DailyQuestionDayState.AlreadyAnsweredToday(current.toDomain(), lastAttempt.answerText, dayNumber, ordered.size)
            else -> DailyQuestionDayState.ReadyToAnswer(current.toDomain(), dayNumber, ordered.size)
        }
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
