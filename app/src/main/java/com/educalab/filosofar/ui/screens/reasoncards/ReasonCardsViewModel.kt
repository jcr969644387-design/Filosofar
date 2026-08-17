package com.educalab.filosofar.ui.screens.reasoncards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.ReasonCardRepository
import com.educalab.filosofar.domain.logic.CoherenceResult
import com.educalab.filosofar.domain.logic.ReasonCoherenceEngine
import com.educalab.filosofar.domain.model.ReasonCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ReasonCardsUiState(
    val availableCards: List<ReasonCard> = emptyList(),
    val selectedCardIds: Set<String> = emptySet(),
    val result: CoherenceResult? = null
)

class ReasonCardsViewModel(private val repository: ReasonCardRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ReasonCardsUiState())
    val uiState: StateFlow<ReasonCardsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val sample = repository.randomSample(8)
            _uiState.value = ReasonCardsUiState(availableCards = sample)
        }
    }

    fun toggleCard(cardId: String) {
        val current = _uiState.value
        val newSelection = if (cardId in current.selectedCardIds) {
            current.selectedCardIds - cardId
        } else if (current.selectedCardIds.size < 4) {
            current.selectedCardIds + cardId
        } else current.selectedCardIds
        _uiState.value = current.copy(selectedCardIds = newSelection, result = null)
    }

    fun evaluate() {
        val current = _uiState.value
        val selected = current.availableCards.filter { it.id in current.selectedCardIds }
        _uiState.value = current.copy(result = ReasonCoherenceEngine.evaluate(selected))
    }
}
