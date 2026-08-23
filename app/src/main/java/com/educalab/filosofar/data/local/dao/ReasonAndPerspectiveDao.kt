package com.educalab.filosofar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.educalab.filosofar.data.local.entity.PerspectiveAttemptEntity
import com.educalab.filosofar.data.local.entity.PerspectiveExerciseEntity
import com.educalab.filosofar.data.local.entity.ReasonCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReasonCardDao {
    @Query("SELECT * FROM reason_card ORDER BY id ASC")
    fun observeAll(): Flow<List<ReasonCardEntity>>

    @Query("SELECT * FROM reason_card ORDER BY RANDOM() LIMIT :limit")
    suspend fun randomSample(limit: Int): List<ReasonCardEntity>

    @Query("SELECT COUNT(*) FROM reason_card")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cards: List<ReasonCardEntity>)
}

@Dao
interface PerspectiveDao {
    @Query("SELECT * FROM perspective_exercise WHERE islandId = :islandId ORDER BY orderInIsland ASC")
    fun observeByIsland(islandId: String): Flow<List<PerspectiveExerciseEntity>>

    @Query("SELECT * FROM perspective_exercise WHERE islandId = :islandId ORDER BY orderInIsland ASC")
    suspend fun listByIslandOnce(islandId: String): List<PerspectiveExerciseEntity>

    @Query("SELECT DISTINCT exerciseId FROM perspective_attempt pa JOIN perspective_exercise p ON p.id = pa.exerciseId WHERE p.islandId = :islandId")
    fun observeCompletedInIsland(islandId: String): Flow<List<String>>

    @Query("SELECT * FROM perspective_exercise WHERE id = :id LIMIT 1")
    suspend fun get(id: String): PerspectiveExerciseEntity?

    @Query("SELECT COUNT(*) FROM perspective_exercise")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<PerspectiveExerciseEntity>)

    @Insert
    suspend fun insertAttempt(attempt: PerspectiveAttemptEntity): Long

    @Query("SELECT * FROM perspective_attempt ORDER BY attemptedAtEpochMs DESC")
    fun observeAllAttempts(): Flow<List<PerspectiveAttemptEntity>>

    @Query("SELECT * FROM perspective_attempt WHERE exerciseId = :exerciseId ORDER BY attemptedAtEpochMs DESC LIMIT 1")
    suspend fun latestAttemptFor(exerciseId: String): PerspectiveAttemptEntity?

    @Query(
        """
        SELECT COUNT(DISTINCT exerciseId) FROM perspective_attempt pa
        JOIN perspective_exercise p ON p.id = pa.exerciseId
        WHERE p.islandId = :islandId
        """
    )
    suspend fun countCompletedInIsland(islandId: String): Int

    @Query("SELECT COUNT(*) FROM perspective_attempt WHERE revealedOtherRole = 1")
    suspend fun countRevealedOtherRole(): Int
}
