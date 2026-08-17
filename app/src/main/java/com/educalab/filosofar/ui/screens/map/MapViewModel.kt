package com.educalab.filosofar.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.local.entity.UserProfileEntity
import com.educalab.filosofar.data.repository.ProgressRepository
import com.educalab.filosofar.data.repository.UserProfileRepository
import com.educalab.filosofar.domain.model.Island
import com.educalab.filosofar.domain.model.IslandProgress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MapUiState(
    val profile: UserProfileEntity? = null,
    val islands: List<Pair<Island, IslandProgress>> = emptyList(),
    val totalCrystals: Int = 0
)

class MapViewModel(
    private val progressRepository: ProgressRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    val uiState: StateFlow<MapUiState> = combine(
        progressRepository.observeIslandsWithProgress(),
        progressRepository.observeTotalCrystals(),
        userProfileRepository.observeProfile()
    ) { islands, crystals, profile ->
        MapUiState(profile = profile, islands = islands, totalCrystals = crystals)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MapUiState())

    init {
        viewModelScope.launch { progressRepository.recalculateAll() }
    }
}
