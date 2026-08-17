package com.educalab.filosofar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.educalab.filosofar.data.local.entity.OpinionRevisionEntity
import com.educalab.filosofar.data.local.entity.ReflectionEntity
import com.educalab.filosofar.data.local.entity.VoiceReflectionMetadataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReflectionDao {

    @Insert
    suspend fun insertReflection(reflection: ReflectionEntity): Long

    @Query("SELECT * FROM reflection ORDER BY createdAtEpochMs DESC")
    fun observeAll(): Flow<List<ReflectionEntity>>

    @Query("DELETE FROM reflection WHERE id = :id")
    suspend fun deleteReflection(id: Long)

    @Insert
    suspend fun insertVoiceMetadata(metadata: VoiceReflectionMetadataEntity): Long

    @Query("SELECT * FROM voice_reflection_metadata WHERE reflectionId = :reflectionId LIMIT 1")
    suspend fun voiceMetadataFor(reflectionId: Long): VoiceReflectionMetadataEntity?

    @Query("SELECT * FROM voice_reflection_metadata ORDER BY recordedAtEpochMs DESC")
    fun observeAllVoiceMetadata(): Flow<List<VoiceReflectionMetadataEntity>>

    @Insert
    suspend fun insertOpinionRevision(revision: OpinionRevisionEntity): Long

    @Query("SELECT * FROM opinion_revision ORDER BY revisedAtEpochMs DESC")
    fun observeAllOpinionRevisions(): Flow<List<OpinionRevisionEntity>>

    @Query("SELECT COUNT(*) FROM opinion_revision WHERE opinionChanged = 1")
    suspend fun countOpinionsChanged(): Int
}
