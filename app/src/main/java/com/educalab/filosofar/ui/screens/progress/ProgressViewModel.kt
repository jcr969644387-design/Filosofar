package com.educalab.filosofar.ui.screens.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.ProgressRepository
import com.educalab.filosofar.domain.model.Badge
import com.educalab.filosofar.domain.model.Island
import com.educalab.filosofar.domain.model.IslandProgress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ProgressUiState(
    val islands: List<Pair<Island, IslandProgress>> = emptyList(),
    val badges: List<Badge> = emptyList(),
    val totalCrystals: Int = 0
)

class ProgressViewModel(repository: ProgressRepository) : ViewModel() {
    val uiState: StateFlow<ProgressUiState> = combine(
        repository.observeIslandsWithProgress(),
        repository.observeBadges(),
        repository.observeTotalCrystals()
    ) { islands, badges, crystals ->
        ProgressUiState(islands, badges, crystals)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProgressUiState())
}
