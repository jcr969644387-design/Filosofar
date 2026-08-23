package com.educalab.filosofar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.educalab.filosofar.data.local.entity.DilemmaAttemptEntity
import com.educalab.filosofar.data.local.entity.DilemmaEntity
import com.educalab.filosofar.data.local.entity.DilemmaOptionEntity
import com.educalab.filosofar.data.local.entity.DilemmaUnlockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DilemmaDao {

    @Query("SELECT * FROM dilemma WHERE islandId = :islandId ORDER BY orderInIsland ASC")
    fun observeByIsland(islandId: String): Flow<List<DilemmaEntity>>

    @Query("SELECT * FROM dilemma WHERE islandId = :islandId ORDER BY orderInIsland ASC")
    suspend fun listByIslandOnce(islandId: String): List<DilemmaEntity>

    @Query("SELECT * FROM dilemma_unlock WHERE islandId = :islandId LIMIT 1")
    suspend fun getUnlockAnchor(islandId: String): DilemmaUnlockEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUnlockAnchor(entity: DilemmaUnlockEntity)

    @Query("SELECT DISTINCT dilemmaId FROM dilemma_attempt da JOIN dilemma d ON d.id = da.dilemmaId WHERE d.islandId = :islandId")
    fun observeCompletedInIsland(islandId: String): Flow<List<String>>

    @Query("SELECT * FROM dilemma WHERE id = :id LIMIT 1")
    suspend fun get(id: String): DilemmaEntity?

    @Query("SELECT * FROM dilemma_option WHERE dilemmaId = :dilemmaId ORDER BY sortOrder ASC")
    suspend fun optionsFor(dilemmaId: String): List<DilemmaOptionEntity>

    @Query("SELECT COUNT(*) FROM dilemma")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDilemmas(dilemmas: List<DilemmaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptions(options: List<DilemmaOptionEntity>)

    @Insert
    suspend fun insertAttempt(attempt: DilemmaAttemptEntity): Long

    @Query("SELECT * FROM dilemma_attempt ORDER BY attemptedAtEpochMs DESC")
    fun observeAllAttempts(): Flow<List<DilemmaAttemptEntity>>

    @Query("SELECT * FROM dilemma_attempt WHERE dilemmaId = :dilemmaId ORDER BY attemptedAtEpochMs DESC LIMIT 1")
    suspend fun latestAttemptFor(dilemmaId: String): DilemmaAttemptEntity?

    @Query("SELECT COUNT(DISTINCT dilemmaId) FROM dilemma_attempt da JOIN dilemma d ON d.id = da.dilemmaId WHERE d.islandId = :islandId")
    suspend fun countCompletedInIsland(islandId: String): Int

    @Query("SELECT COUNT(*) FROM dilemma_attempt WHERE viewedAlternativePerspective = 1")
    suspend fun countPerspectivesViewedInDilemmas(): Int
}
