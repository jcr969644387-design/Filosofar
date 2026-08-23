package com.educalab.filosofar.ui.screens.logiclab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.LogicRepository
import com.educalab.filosofar.domain.model.LogicChallenge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LogicLabListUiState(
    val challenges: List<LogicChallenge> = emptyList(),
    val unlockedCount: Int = 0,
    val completedIds: Set<String> = emptySet()
)

class LogicLabViewModel(islandId: String, private val repository: LogicRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LogicLabListUiState())
    val uiState: StateFlow<LogicLabListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val unlockState = repository.getIslandUnlockState(islandId)
            _uiState.value = _uiState.value.copy(challenges = unlockState.challenges, unlockedCount = unlockState.unlockedCount)
            repository.observeCompletedIds(islandId).collect { completed ->
                _uiState.value = _uiState.value.copy(completedIds = completed)
            }
        }
    }
}
