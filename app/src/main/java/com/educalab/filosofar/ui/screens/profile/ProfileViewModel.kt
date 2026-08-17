package com.educalab.filosofar.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.local.entity.UserProfileEntity
import com.educalab.filosofar.data.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProfileSetupState(
    val alias: String = "",
    val selectedAvatarId: Int = 0,
    val isSaving: Boolean = false
)

class ProfileViewModel(private val repository: UserProfileRepository) : ViewModel() {

    val profile: StateFlow<UserProfileEntity?> = repository.observeProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _setupState = MutableStateFlow(ProfileSetupState())
    val setupState: StateFlow<ProfileSetupState> = _setupState

    fun updateAlias(alias: String) {
        _setupState.value = _setupState.value.copy(alias = alias.take(18))
    }

    fun selectAvatar(avatarId: Int) {
        _setupState.value = _setupState.value.copy(selectedAvatarId = avatarId)
    }

    fun createProfileAndFinishOnboarding(onDone: () -> Unit) {
        val state = _setupState.value
        viewModelScope.launch {
            _setupState.value = state.copy(isSaving = true)
            repository.createProfile(state.alias, state.selectedAvatarId)
            repository.completeOnboarding()
            _setupState.value = state.copy(isSaving = false)
            onDone()
        }
    }

    fun toggleSound(enabled: Boolean) = viewModelScope.launch { repository.setSoundEnabled(enabled) }
    fun toggleHaptics(enabled: Boolean) = viewModelScope.launch { repository.setHapticsEnabled(enabled) }
    fun updateAvatarLater(avatarId: Int) = viewModelScope.launch { repository.updateAvatar(avatarId) }
    fun updateAliasLater(alias: String) = viewModelScope.launch { repository.updateAlias(alias) }

    fun touchLastOpened() = viewModelScope.launch { repository.touchLastOpened() }
}
