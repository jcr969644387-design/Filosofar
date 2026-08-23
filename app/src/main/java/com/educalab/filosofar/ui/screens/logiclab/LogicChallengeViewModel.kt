package com.educalab.filosofar.ui.screens.logiclab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.LogicRepository
import com.educalab.filosofar.domain.logic.LogicCheckResult
import com.educalab.filosofar.domain.logic.LogicChallengeEngine
import com.educalab.filosofar.domain.model.LogicChallenge
import com.educalab.filosofar.domain.model.LogicChallengeType
import com.educalab.filosofar.domain.model.LogicItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LogicChallengeUiState(
    val challenge: LogicChallenge? = null,
    val alreadySolved: Boolean = false,
    // SEQUENCE
    val sequencePool: List<LogicItem> = emptyList(),
    val placedOrder: List<LogicItem> = emptyList(),
    // MATCH
    val premises: List<LogicItem> = emptyList(),
    val conclusionsShuffled: List<LogicItem> = emptyList(),
    val selectedPremiseId: String? = null,
    val pendingConclusionId: String? = null,
    val lastConnectionWrong: Boolean = false,
    val matchedPairs: Map<String, String> = emptyMap(),
    // SPOT_FLAW
    val selectedFlawId: String? = null,
    // shared
    val checked: Boolean = false,
    val result: LogicCheckResult? = null,
    val attemptsUsed: Int = 0
)

class LogicChallengeViewModel(
    private val challengeId: String,
    private val repository: LogicRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogicChallengeUiState())
    val uiState: StateFlow<LogicChallengeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val challenge = repository.getFull(challengeId) ?: return@launch
            val solvedBefore = repository.wasSolved(challengeId)
            _uiState.value = when (challenge.type) {
                LogicChallengeType.SEQUENCE -> {
                    val correctOrder = challenge.items.sortedBy { it.correctPosition }
                    if (solvedBefore) {
                        LogicChallengeUiState(
                            challenge = challenge,
                            alreadySolved = true,
                            sequencePool = correctOrder,
                            placedOrder = correctOrder,
                            checked = true,
                            result = LogicChallengeEngine.checkSequence(challenge, correctOrder.map { it.id })
                        )
                    } else {
                        LogicChallengeUiState(challenge = challenge, sequencePool = challenge.items.shuffled())
                    }
                }
                LogicChallengeType.MATCH -> {
                    val premises = challenge.items.filter { it.role == "PREMISE" }
                    val conclusions = challenge.items.filter { it.role == "CONCLUSION" }
                    if (solvedBefore) {
                        val correctPairs = premises.associate { p -> p.id to conclusions.first { it.pairKey == p.pairKey }.id }
                        LogicChallengeUiState(
                            challenge = challenge,
                            alreadySolved = true,
                            premises = premises,
                            conclusionsShuffled = conclusions,
                            matchedPairs = correctPairs,
                            checked = true,
                            result = LogicChallengeEngine.checkMatch(challenge, correctPairs)
                        )
                    } else {
                        LogicChallengeUiState(challenge = challenge, premises = premises, conclusionsShuffled = conclusions.shuffled())
                    }
                }
                LogicChallengeType.SPOT_FLAW -> {
                    if (solvedBefore) {
                        val flawedId = challenge.items.first { it.isFlawed }.id
                        LogicChallengeUiState(
                            challenge = challenge,
                            alreadySolved = true,
                            selectedFlawId = flawedId,
                            checked = true,
                            result = LogicChallengeEngine.checkSpotFlaw(challenge, flawedId)
                        )
                    } else {
                        LogicChallengeUiState(challenge = challenge)
                    }
                }
            }
        }
    }

    // --- SEQUENCE ---
    fun tapSequenceItem(item: LogicItem) {
        val s = _uiState.value
        if (s.checked || item in s.placedOrder) return
        _uiState.value = s.copy(placedOrder = s.placedOrder + item)
    }

    fun undoLastSequenceItem() {
        val s = _uiState.value
        if (s.checked || s.placedOrder.isEmpty()) return
        _uiState.value = s.copy(placedOrder = s.placedOrder.dropLast(1))
    }

    // --- MATCH ---
    fun tapPremise(id: String) {
        val s = _uiState.value
        if (s.checked || s.matchedPairs.containsKey(id)) return
        _uiState.value = s.copy(selectedPremiseId = id, pendingConclusionId = null, lastConnectionWrong = false)
    }

    fun tapConclusion(id: String) {
        val s = _uiState.value
        if (s.checked || s.selectedPremiseId == null || s.matchedPairs.containsValue(id)) return
        _uiState.value = s.copy(pendingConclusionId = id, lastConnectionWrong = false)
    }

    /** Confirma la conexión pendiente: solo se guarda si premisa y conclusión comparten pairKey. */
    fun confirmPendingMatch() {
        val s = _uiState.value
        val premiseId = s.selectedPremiseId ?: return
        val conclusionId = s.pendingConclusionId ?: return
        val premise = s.premises.firstOrNull { it.id == premiseId } ?: return
        val conclusion = s.conclusionsShuffled.firstOrNull { it.id == conclusionId } ?: return
        val isCorrect = premise.pairKey.isNotEmpty() && premise.pairKey == conclusion.pairKey
        _uiState.value = if (isCorrect) {
            s.copy(
                matchedPairs = s.matchedPairs + (premiseId to conclusionId),
                selectedPremiseId = null,
                pendingConclusionId = null,
                lastConnectionWrong = false
            )
        } else {
            s.copy(selectedPremiseId = null, pendingConclusionId = null, lastConnectionWrong = true)
        }
    }

    // --- SPOT_FLAW ---
    fun tapFlawCandidate(id: String) {
        if (_uiState.value.checked) return
        _uiState.value = _uiState.value.copy(selectedFlawId = id)
    }

    fun canCheck(): Boolean {
        val s = _uiState.value
        val challenge = s.challenge ?: return false
        return when (challenge.type) {
            LogicChallengeType.SEQUENCE -> s.placedOrder.size == challenge.items.size
            LogicChallengeType.MATCH -> s.matchedPairs.size == s.premises.size
            LogicChallengeType.SPOT_FLAW -> s.selectedFlawId != null
        }
    }

    fun checkAnswer() {
        val s = _uiState.value
        val challenge = s.challenge ?: return
        val result: LogicCheckResult = when (challenge.type) {
            LogicChallengeType.SEQUENCE -> LogicChallengeEngine.checkSequence(challenge, s.placedOrder.map { it.id })
            LogicChallengeType.MATCH -> LogicChallengeEngine.checkMatch(challenge, s.matchedPairs)
            LogicChallengeType.SPOT_FLAW -> LogicChallengeEngine.checkSpotFlaw(challenge, s.selectedFlawId!!)
        }
        val wasCorrect = when (result) {
            is LogicCheckResult.Sequence -> result.correct
            is LogicCheckResult.Match -> result.allCorrect
            is LogicCheckResult.SpotFlaw -> result.correct
        }
        val newAttempts = s.attemptsUsed + 1
        _uiState.value = s.copy(checked = true, result = result, attemptsUsed = newAttempts)
        viewModelScope.launch { repository.recordAttempt(challengeId, wasCorrect, newAttempts) }
    }

    fun retry() {
        val s = _uiState.value
        val challenge = s.challenge ?: return
        _uiState.value = when (challenge.type) {
            LogicChallengeType.SEQUENCE -> s.copy(placedOrder = emptyList(), checked = false, result = null)
            LogicChallengeType.MATCH -> s.copy(matchedPairs = emptyMap(), selectedPremiseId = null, pendingConclusionId = null, lastConnectionWrong = false, checked = false, result = null)
            LogicChallengeType.SPOT_FLAW -> s.copy(selectedFlawId = null, checked = false, result = null)
        }
    }
}
