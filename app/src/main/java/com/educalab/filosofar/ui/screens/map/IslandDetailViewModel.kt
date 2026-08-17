package com.educalab.filosofar.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.ProgressRepository
import com.educalab.filosofar.domain.model.Island
import com.educalab.filosofar.domain.model.IslandProgress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class IslandDetailViewModel(
    private val islandId: String,
    progressRepository: ProgressRepository
) : ViewModel() {

    val islandWithProgress: StateFlow<Pair<Island, IslandProgress>?> =
        progressRepository.observeIslandsWithProgress()
            .map { list -> list.firstOrNull { it.first.id == islandId } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
