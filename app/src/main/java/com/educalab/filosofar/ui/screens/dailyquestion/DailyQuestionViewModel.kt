package com.educalab.filosofar.ui.screens.dailyquestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.DailyQuestionDayState
import com.educalab.filosofar.data.repository.DailyQuestionRepository
import com.educalab.filosofar.domain.model.DailyQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DailyQuestionUiState(
    val question: DailyQuestion? = null,
    val answerText: String = "",
    val submitted: Boolean = false,
    val previousAnswer: String? = null,
    val loading: Boolean = true,
    val waitingForTomorrow: Boolean = false,
    val allCompleted: Boolean = false,
    val dayNumber: Int = 0,
    val totalDays: Int = 0
)

class DailyQuestionViewModel(
    private val islandId: String,
    private val repository: DailyQuestionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyQuestionUiState())
    val uiState: StateFlow<DailyQuestionUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            when (val status = repository.getTodayStatus(islandId)) {
                is DailyQuestionDayState.ReadyToAnswer -> _uiState.value = DailyQuestionUiState(
                    question = status.question,
                    loading = false,
                    dayNumber = status.dayNumber,
                    totalDays = status.totalDays
                )
                is DailyQuestionDayState.AlreadyAnsweredToday -> _uiState.value = DailyQuestionUiState(
                    question = status.question,
                    loading = false,
                    submitted = true,
                    previousAnswer = status.answerText,
                    waitingForTomorrow = true,
                    dayNumber = status.dayNumber,
                    totalDays = status.totalDays
                )
                DailyQuestionDayState.AllCompleted -> _uiState.value = DailyQuestionUiState(loading = false, allCompleted = true)
                DailyQuestionDayState.NoQuestions -> _uiState.value = DailyQuestionUiState(loading = false)
            }
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
            refresh()
        }
    }
}
