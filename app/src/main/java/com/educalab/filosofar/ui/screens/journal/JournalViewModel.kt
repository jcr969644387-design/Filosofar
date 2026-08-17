package com.educalab.filosofar.ui.screens.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educalab.filosofar.data.local.entity.ReflectionEntity
import com.educalab.filosofar.data.local.entity.VoiceReflectionMetadataEntity
import com.educalab.filosofar.data.repository.ReflectionRepository
import com.educalab.filosofar.util.AudioRecorderManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class JournalUiState(
    val entries: List<ReflectionEntity> = emptyList(),
    val voiceByReflectionId: Map<Long, VoiceReflectionMetadataEntity> = emptyMap(),
    val newTitle: String = "",
    val newBody: String = "",
    val isRecording: Boolean = false,
    val recordingElapsedMs: Long = 0L,
    val lastRecordedFilePath: String? = null,
    val lastRecordedDurationMs: Long = 0L
)

class JournalViewModel(
    private val repository: ReflectionRepository,
    private val audioRecorderManager: AudioRecorderManager
) : ViewModel() {

    private val _formState = MutableStateFlow(JournalUiState())

    val uiState: StateFlow<JournalUiState> = combine(
        repository.observeJournal(),
        repository.observeVoiceMetadata(),
        _formState
    ) { entries, voiceList, form ->
        form.copy(entries = entries, voiceByReflectionId = voiceList.associateBy { it.reflectionId })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), JournalUiState())

    fun updateTitle(title: String) { _formState.value = _formState.value.copy(newTitle = title) }
    fun updateBody(body: String) { _formState.value = _formState.value.copy(newBody = body) }

    fun startRecording() {
        val file = audioRecorderManager.startRecording()
        if (file != null) {
            _formState.value = _formState.value.copy(isRecording = true)
        }
    }

    fun stopRecording() {
        val result = audioRecorderManager.stopRecording()
        _formState.value = _formState.value.copy(
            isRecording = false,
            lastRecordedFilePath = result?.first?.absolutePath,
            lastRecordedDurationMs = result?.second ?: 0L
        )
    }

    fun cancelRecording() {
        audioRecorderManager.cancelRecording()
        _formState.value = _formState.value.copy(isRecording = false, lastRecordedFilePath = null, lastRecordedDurationMs = 0L)
    }

    fun discardRecording() {
        _formState.value.lastRecordedFilePath?.let { audioRecorderManager.deleteRecording(it) }
        _formState.value = _formState.value.copy(lastRecordedFilePath = null, lastRecordedDurationMs = 0L)
    }

    fun playRecording(path: String) {
        audioRecorderManager.play(java.io.File(path)) { }
    }

    fun saveEntry() {
        val form = _formState.value
        if (form.newBody.isBlank()) return
        viewModelScope.launch {
            val id = repository.addJournalEntry(null, form.newTitle, form.newBody)
            form.lastRecordedFilePath?.let { path ->
                repository.attachVoiceReflection(id, path, form.lastRecordedDurationMs)
            }
            _formState.value = JournalUiState()
        }
    }

    fun deleteEntry(id: Long) = viewModelScope.launch { repository.deleteJournalEntry(id) }

    override fun onCleared() {
        super.onCleared()
        audioRecorderManager.stopPlaybackIfAny()
    }
}
