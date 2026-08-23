package com.educalab.filosofar.ui.screens.perspective

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.PerspectiveRepository
import com.educalab.filosofar.domain.model.PerspectiveExercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PerspectiveListUiState(
    val exercises: List<PerspectiveExercise> = emptyList(),
    val unlockedCount: Int = 0,
    val completedIds: Set<String> = emptySet()
)

class PerspectiveListViewModel(islandId: String, private val repository: PerspectiveRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(PerspectiveListUiState())
    val uiState: StateFlow<PerspectiveListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val unlockState = repository.getIslandUnlockState(islandId)
            _uiState.value = _uiState.value.copy(exercises = unlockState.exercises, unlockedCount = unlockState.unlockedCount)
            repository.observeCompletedIds(islandId).collect { completed ->
                _uiState.value = _uiState.value.copy(completedIds = completed)
            }
        }
    }
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
