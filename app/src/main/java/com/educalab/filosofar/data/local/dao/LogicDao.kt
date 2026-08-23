package com.educalab.filosofar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.educalab.filosofar.data.local.entity.LogicAttemptEntity
import com.educalab.filosofar.data.local.entity.LogicChallengeEntity
import com.educalab.filosofar.data.local.entity.LogicChallengeItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LogicDao {

    @Query("SELECT * FROM logic_challenge WHERE islandId = :islandId ORDER BY orderInIsland ASC")
    fun observeByIsland(islandId: String): Flow<List<LogicChallengeEntity>>

    @Query("SELECT * FROM logic_challenge WHERE islandId = :islandId ORDER BY orderInIsland ASC")
    suspend fun listByIslandOnce(islandId: String): List<LogicChallengeEntity>

    @Query("SELECT DISTINCT challengeId FROM logic_attempt la JOIN logic_challenge c ON c.id = la.challengeId WHERE c.islandId = :islandId AND la.wasCorrect = 1")
    fun observeCompletedInIsland(islandId: String): Flow<List<String>>

    @Query("SELECT * FROM logic_challenge WHERE id = :id LIMIT 1")
    suspend fun get(id: String): LogicChallengeEntity?

    @Query("SELECT * FROM logic_challenge_item WHERE challengeId = :challengeId ORDER BY displayOrder ASC")
    suspend fun itemsFor(challengeId: String): List<LogicChallengeItemEntity>

    @Query("SELECT COUNT(*) FROM logic_challenge")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenges(challenges: List<LogicChallengeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<LogicChallengeItemEntity>)

    @Insert
    suspend fun insertAttempt(attempt: LogicAttemptEntity): Long

    @Query("SELECT * FROM logic_attempt ORDER BY attemptedAtEpochMs DESC")
    fun observeAllAttempts(): Flow<List<LogicAttemptEntity>>

    @Query("SELECT * FROM logic_attempt WHERE challengeId = :challengeId AND wasCorrect = 1 ORDER BY attemptedAtEpochMs DESC LIMIT 1")
    suspend fun latestCorrectAttemptFor(challengeId: String): LogicAttemptEntity?

    @Query(
        """
        SELECT COUNT(DISTINCT challengeId) FROM logic_attempt la
        JOIN logic_challenge c ON c.id = la.challengeId
        WHERE c.islandId = :islandId AND la.wasCorrect = 1
        """
    )
    suspend fun countSolvedInIsland(islandId: String): Int

    @Query(
        """
        SELECT challengeId FROM logic_attempt
        WHERE wasCorrect = 0
        GROUP BY challengeId
        ORDER BY MAX(attemptedAtEpochMs) DESC
        LIMIT :limit
        """
    )
    suspend fun recentFailedChallengeIds(limit: Int): List<String>
}
