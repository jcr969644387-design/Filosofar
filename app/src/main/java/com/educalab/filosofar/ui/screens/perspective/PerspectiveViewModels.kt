package com.educalab.filosofar.ui.screens.perspective

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.PerspectiveRepository
import com.educalab.filosofar.domain.model.PerspectiveExercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PerspectiveListViewModel(islandId: String, repository: PerspectiveRepository) : ViewModel() {
    val exercises: StateFlow<List<PerspectiveExercise>> = repository.observeByIsland(islandId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

data class PerspectiveDetailUiState(
    val exercise: PerspectiveExercise? = null,
    val chosenRole: String? = null,
    val revealedOther: Boolean = false,
    val reflectionAnswer: String = "",
    val confirmed: Boolean = false
)

class PerspectiveDetailViewModel(
    private val exerciseId: String,
    private val repository: PerspectiveRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PerspectiveDetailUiState())
    val uiState: StateFlow<PerspectiveDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val exercise = repository.getFull(exerciseId)
            val prior = repository.latestAttempt(exerciseId)
            _uiState.value = if (prior != null) {
                PerspectiveDetailUiState(
                    exercise = exercise,
                    chosenRole = prior.choseRole,
                    revealedOther = true,
                    reflectionAnswer = prior.reflectionAnswer,
                    confirmed = true
                )
            } else {
                PerspectiveDetailUiState(exercise = exercise)
            }
        }
    }

    fun chooseRole(role: String) {
        _uiState.value = _uiState.value.copy(chosenRole = role)
    }

    fun revealOther() {
        _uiState.value = _uiState.value.copy(revealedOther = true)
    }

    fun updateReflectionAnswer(text: String) {
        _uiState.value = _uiState.value.copy(reflectionAnswer = text)
    }

    fun confirm() {
        val state = _uiState.value
        val role = state.chosenRole ?: return
        viewModelScope.launch {
            repository.recordAttempt(exerciseId, role, state.revealedOther, state.reflectionAnswer)
            _uiState.value = state.copy(confirmed = true)
        }
    }
}
