package com.educalab.filosofar.ui.screens.selfdebate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.SelfDebateRepository
import com.educalab.filosofar.domain.model.SelfDebate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SelfDebateListUiState(
    val debates: List<SelfDebate> = emptyList(),
    val unlockedCount: Int = 0,
    val completedIds: Set<String> = emptySet()
)

class SelfDebateListViewModel(islandId: String, private val repository: SelfDebateRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SelfDebateListUiState())
    val uiState: StateFlow<SelfDebateListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val unlockState = repository.getIslandUnlockState(islandId)
            _uiState.value = _uiState.value.copy(debates = unlockState.debates, unlockedCount = unlockState.unlockedCount)
            repository.observeCompletedIds(islandId).collect { completed ->
                _uiState.value = _uiState.value.copy(completedIds = completed)
            }
        }
    }
}

data class SelfDebateUiState(
    val debate: SelfDebate? = null,
    val placedOnA: List<String> = emptyList(),
    val placedOnB: List<String> = emptyList(),
    val conclusionText: String = "",
    val confirmed: Boolean = false,
    val correctCount: Int = 0
)

class SelfDebateDetailViewModel(
    private val debateId: String,
    private val repository: SelfDebateRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SelfDebateUiState())
    val uiState: StateFlow<SelfDebateUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = SelfDebateUiState(debate = repository.getFull(debateId))
        }
    }

    fun placeArgument(argumentId: String, side: String) {
        val s = _uiState.value
        val cleanedA = s.placedOnA - argumentId
        val cleanedB = s.placedOnB - argumentId
        _uiState.value = if (side == "A") {
            s.copy(placedOnA = cleanedA + argumentId, placedOnB = cleanedB)
        } else {
            s.copy(placedOnA = cleanedA, placedOnB = cleanedB + argumentId)
        }
    }

    fun updateConclusion(text: String) {
        _uiState.value = _uiState.value.copy(conclusionText = text)
    }

    fun unplacedArguments(): List<String> {
        val s = _uiState.value
        val debate = s.debate ?: return emptyList()
        val placed = (s.placedOnA + s.placedOnB).toSet()
        return debate.arguments.map { it.id }.filter { it !in placed }
    }

    fun confirm() {
        val s = _uiState.value
        val debate = s.debate ?: return
        val correct = debate.arguments.count { arg ->
            (arg.correctSide == "A" && arg.id in s.placedOnA) || (arg.correctSide == "B" && arg.id in s.placedOnB)
        }
        viewModelScope.launch {
            repository.recordAttempt(debateId, correct, debate.arguments.size, s.conclusionText)
            _uiState.value = s.copy(confirmed = true, correctCount = correct)
        }
    }
}
