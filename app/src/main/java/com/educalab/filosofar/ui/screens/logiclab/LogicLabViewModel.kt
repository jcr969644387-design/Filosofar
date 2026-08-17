package com.educalab.filosofar.ui.screens.logiclab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.repository.LogicRepository
import com.educalab.filosofar.domain.model.LogicChallenge
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class LogicLabViewModel(islandId: String, repository: LogicRepository) : ViewModel() {
    val challenges: StateFlow<List<LogicChallenge>> = repository.observeByIsland(islandId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
