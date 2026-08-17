package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.ReflectionDao
import com.educalab.filosofar.data.local.entity.OpinionRevisionEntity
import com.educalab.filosofar.data.local.entity.ReflectionEntity
import com.educalab.filosofar.data.local.entity.VoiceReflectionMetadataEntity
import kotlinx.coroutines.flow.Flow

class ReflectionRepository(
    private val dao: ReflectionDao,
    private val progressRepository: ProgressRepository
) {
    fun observeJournal(): Flow<List<ReflectionEntity>> = dao.observeAll()

    fun observeVoiceMetadata(): Flow<List<VoiceReflectionMetadataEntity>> = dao.observeAllVoiceMetadata()

    fun observeOpinionRevisions(): Flow<List<OpinionRevisionEntity>> = dao.observeAllOpinionRevisions()

    suspend fun addJournalEntry(islandId: String?, title: String, body: String): Long {
        val id = dao.insertReflection(
            ReflectionEntity(
                relatedIslandId = islandId,
                title = title.trim().ifBlank { "Idea sin título" }.take(60),
                bodyText = body.trim(),
                createdAtEpochMs = System.currentTimeMillis()
            )
        )
        progressRepository.recalculateAll()
        return id
    }

    suspend fun deleteJournalEntry(id: Long) = dao.deleteReflection(id)

    /** Guarda solo metadatos: ruta local, duración. Nunca transcribe ni sube el audio. */
    suspend fun attachVoiceReflection(reflectionId: Long, filePath: String, durationMs: Long) {
        dao.insertVoiceMetadata(
            VoiceReflectionMetadataEntity(
                reflectionId = reflectionId,
                filePath = filePath,
                durationMs = durationMs.coerceAtMost(MAX_DURATION_MS),
                recordedAtEpochMs = System.currentTimeMillis()
            )
        )
    }

    suspend fun recordOpinionRevision(
        questionId: String,
        previousAnswerSnapshot: String,
        newAnswerText: String,
        opinionChanged: Boolean,
        whatChangedMyMind: String
    ) {
        dao.insertOpinionRevision(
            OpinionRevisionEntity(
                questionId = questionId,
                previousAnswerSnapshot = previousAnswerSnapshot,
                newAnswerText = newAnswerText.trim(),
                opinionChanged = opinionChanged,
                whatChangedMyMind = whatChangedMyMind.trim(),
                revisedAtEpochMs = System.currentTimeMillis()
            )
        )
        progressRepository.recalculateAll()
    }

    companion object {
        const val MAX_DURATION_MS = 45_000L
    }
}
