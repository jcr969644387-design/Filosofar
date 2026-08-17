package com.educalab.filosofar.ui.screens.dailyquestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.DailyQuestionRepository
import com.educalab.filosofar.domain.model.DailyQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class DailyQuestionUiState(
    val question: DailyQuestion? = null,
    val answerText: String = "",
    val submitted: Boolean = false,
    val previousAnswer: String? = null,
    val loading: Boolean = true
)

class DailyQuestionViewModel(
    private val islandId: String,
    private val repository: DailyQuestionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyQuestionUiState())
    val uiState: StateFlow<DailyQuestionUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val question = repository.observeByIsland(islandId).let { flow ->
                flow.first()
            }.let { list ->
                // Prioriza una sin responder dentro de la isla; si todas tienen intento, cualquiera.
                list.firstOrNull { repository.lastAnswerFor(it.id) == null } ?: list.randomOrNull()
            }
            val previous = question?.let { repository.lastAnswerFor(it.id) }
            _uiState.value = DailyQuestionUiState(question = question, previousAnswer = previous, loading = false)
        }
    }

    fun updateAnswer(text: String) {
        _uiState.value = _uiState.value.copy(answerText = text)
    }

    fun submit() {
        val state = _uiState.value
        val question = state.question ?: return
        if (state.answerText.isBlank()) return
        viewModelScope.launch {
            repository.submitAnswer(question.id, state.answerText)
            _uiState.value = state.copy(submitted = true)
        }
    }
}
