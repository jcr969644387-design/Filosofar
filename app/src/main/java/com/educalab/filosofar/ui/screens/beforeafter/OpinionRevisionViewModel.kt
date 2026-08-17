package com.educalab.filosofar.ui.screens.beforeafter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.DailyQuestionRepository
import com.educalab.filosofar.data.repository.ReflectionRepository
import com.educalab.filosofar.domain.model.DailyQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class OpinionRevisionUiState(
    val question: DailyQuestion? = null,
    val previousAnswer: String = "",
    val newAnswerText: String = "",
    val whatChanged: String = "",
    val hasAnyAnsweredQuestion: Boolean = true,
    val saved: Boolean = false,
    val opinionChangedResult: Boolean? = null,
    val loading: Boolean = true
)

/**
 * "Antes pensaba / Ahora pienso": toma una pregunta ya respondida antes y
 * deja que el usuario escriba su respuesta de hoy. El cambio de opinión se
 * detecta comparando el texto (no es un juicio de "mejor/peor"), y siempre
 * queda a la vista tanto la respuesta anterior como la nueva.
 */
class OpinionRevisionViewModel(
    private val questionRepository: DailyQuestionRepository,
    private val reflectionRepository: ReflectionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OpinionRevisionUiState())
    val uiState: StateFlow<OpinionRevisionUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val question = questionRepository.pickQuestionForRevision()
            if (question == null) {
                _uiState.value = OpinionRevisionUiState(hasAnyAnsweredQuestion = false, loading = false)
            } else {
                val previous = questionRepository.lastAnswerFor(question.id).orEmpty()
                _uiState.value = OpinionRevisionUiState(question = question, previousAnswer = previous, loading = false)
            }
        }
    }

    fun updateNewAnswer(text: String) { _uiState.value = _uiState.value.copy(newAnswerText = text) }
    fun updateWhatChanged(text: String) { _uiState.value = _uiState.value.copy(whatChanged = text) }

    fun save() {
        val s = _uiState.value
        val question = s.question ?: return
        if (s.newAnswerText.isBlank()) return
        val changed = normalize(s.newAnswerText) != normalize(s.previousAnswer)
        viewModelScope.launch {
            reflectionRepository.recordOpinionRevision(
                questionId = question.id,
                previousAnswerSnapshot = s.previousAnswer,
                newAnswerText = s.newAnswerText,
                opinionChanged = changed,
                whatChangedMyMind = s.whatChanged
            )
            questionRepository.submitAnswer(question.id, s.newAnswerText)
            _uiState.value = s.copy(saved = true, opinionChangedResult = changed)
        }
    }

    private fun normalize(text: String) = text.trim().lowercase().replace(Regex("\\s+"), " ")
}
