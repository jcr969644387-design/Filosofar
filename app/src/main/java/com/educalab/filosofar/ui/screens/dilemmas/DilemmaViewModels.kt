package com.educalab.filosofar.ui.screens.dilemmas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.DilemmaRepository
import com.educalab.filosofar.domain.model.Dilemma
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DilemmaListViewModel(islandId: String, repository: DilemmaRepository) : ViewModel() {
    val dilemmas: StateFlow<List<Dilemma>> = repository.observeByIsland(islandId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
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
            _uiState.value = DilemmaDetailUiState(dilemma = repository.getFull(dilemmaId))
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
