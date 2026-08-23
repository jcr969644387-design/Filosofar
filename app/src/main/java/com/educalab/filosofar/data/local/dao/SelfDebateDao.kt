package com.educalab.filosofar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.educalab.filosofar.data.local.entity.DebateArgumentEntity
import com.educalab.filosofar.data.local.entity.SelfDebateAttemptEntity
import com.educalab.filosofar.data.local.entity.SelfDebateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SelfDebateDao {

    @Query("SELECT * FROM self_debate WHERE islandId = :islandId ORDER BY orderInIsland ASC")
    fun observeByIsland(islandId: String): Flow<List<SelfDebateEntity>>

    @Query("SELECT * FROM self_debate WHERE islandId = :islandId ORDER BY orderInIsland ASC")
    suspend fun listByIslandOnce(islandId: String): List<SelfDebateEntity>

    @Query("SELECT DISTINCT debateId FROM self_debate_attempt sda JOIN self_debate sd ON sd.id = sda.debateId WHERE sd.islandId = :islandId")
    fun observeCompletedInIsland(islandId: String): Flow<List<String>>

    @Query("SELECT * FROM self_debate WHERE id = :id LIMIT 1")
    suspend fun get(id: String): SelfDebateEntity?

    @Query("SELECT * FROM debate_argument WHERE debateId = :debateId")
    suspend fun argumentsFor(debateId: String): List<DebateArgumentEntity>

    @Query("SELECT COUNT(*) FROM self_debate")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDebates(debates: List<SelfDebateEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArguments(arguments: List<DebateArgumentEntity>)

    @Insert
    suspend fun insertAttempt(attempt: SelfDebateAttemptEntity): Long

    @Query("SELECT * FROM self_debate_attempt ORDER BY attemptedAtEpochMs DESC")
    fun observeAllAttempts(): Flow<List<SelfDebateAttemptEntity>>

    @Query(
        """
        SELECT COUNT(DISTINCT debateId) FROM self_debate_attempt sda
        JOIN self_debate sd ON sd.id = sda.debateId
        WHERE sd.islandId = :islandId
        """
    )
    suspend fun countCompletedInIsland(islandId: String): Int
}
