package com.educalab.filosofar.ui.screens.dilemmas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.DilemmaRepository
import com.educalab.filosofar.domain.model.Dilemma
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DilemmaListUiState(
    val dilemmas: List<Dilemma> = emptyList(),
    val unlockedCount: Int = 0,
    val completedIds: Set<String> = emptySet()
)

class DilemmaListViewModel(islandId: String, private val repository: DilemmaRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(DilemmaListUiState())
    val uiState: StateFlow<DilemmaListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val unlockState = repository.getIslandUnlockState(islandId)
            _uiState.value = _uiState.value.copy(dilemmas = unlockState.dilemmas, unlockedCount = unlockState.unlockedCount)
            repository.observeCompletedIds(islandId).collect { completed ->
                _uiState.value = _uiState.value.copy(completedIds = completed)
            }
        }
    }
}

data class DilemmaDetailUiState(
    val dilemma: Dilemma? = null,
    val selectedOptionId: String? = null,
    val revealedPerspective: Boolean = false,
    val confirmed: Boolean = false
)

class DilemmaDetailViewModel(
    private val dilemmaId: String,
    private val repository: DilemmaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DilemmaDetailUiState())
    val uiState: StateFlow<DilemmaDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val dilemma = repository.getFull(dilemmaId)
            val prior = repository.latestAttempt(dilemmaId)
            _uiState.value = if (prior != null) {
                DilemmaDetailUiState(
                    dilemma = dilemma,
                    selectedOptionId = prior.chosenOptionId,
                    revealedPerspective = true,
                    confirmed = true
                )
            } else {
                DilemmaDetailUiState(dilemma = dilemma)
            }
        }
    }

    fun selectOption(optionId: String) {
        _uiState.value = _uiState.value.copy(selectedOptionId = optionId, revealedPerspective = false)
    }

    fun revealPerspectives() {
        _uiState.value = _uiState.value.copy(revealedPerspective = true)
    }

    fun confirm() {
        val state = _uiState.value
        val optionId = state.selectedOptionId ?: return
        viewModelScope.launch {
            repository.recordAttempt(dilemmaId, optionId, state.revealedPerspective)
            _uiState.value = state.copy(confirmed = true)
        }
    }
}
