package com.educalab.filosofar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.educalab.filosofar.data.local.entity.DailyQuestionEntity
import com.educalab.filosofar.data.local.entity.DailyQuestionUnlockEntity
import com.educalab.filosofar.data.local.entity.QuestionAttemptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyQuestionDao {

    @Query("SELECT * FROM daily_question WHERE islandId = :islandId ORDER BY orderInIsland ASC")
    fun observeByIsland(islandId: String): Flow<List<DailyQuestionEntity>>

    @Query("SELECT * FROM daily_question WHERE islandId = :islandId ORDER BY orderInIsland ASC")
    suspend fun listByIslandOnce(islandId: String): List<DailyQuestionEntity>

    @Query("SELECT * FROM daily_question_unlock WHERE islandId = :islandId LIMIT 1")
    suspend fun getUnlockAnchor(islandId: String): DailyQuestionUnlockEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUnlockAnchor(entity: DailyQuestionUnlockEntity)

    @Query("SELECT * FROM question_attempt WHERE questionId = :questionId ORDER BY answeredAtEpochMs DESC LIMIT 1")
    suspend fun latestAttemptFor(questionId: String): QuestionAttemptEntity?

    @Query("SELECT * FROM daily_question WHERE id = :id LIMIT 1")
    suspend fun get(id: String): DailyQuestionEntity?

    @Query("SELECT COUNT(*) FROM daily_question")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<DailyQuestionEntity>)

    @Query(
        """
        SELECT * FROM daily_question
        WHERE id NOT IN (SELECT DISTINCT questionId FROM question_attempt)
        ORDER BY RANDOM() LIMIT 1
        """
    )
    suspend fun pickUnanswered(): DailyQuestionEntity?

    @Query("SELECT * FROM daily_question ORDER BY RANDOM() LIMIT 1")
    suspend fun pickAny(): DailyQuestionEntity?

    @Insert
    suspend fun insertAttempt(attempt: QuestionAttemptEntity): Long

    @Query("SELECT * FROM question_attempt WHERE questionId = :questionId ORDER BY answeredAtEpochMs DESC")
    fun observeAttemptsFor(questionId: String): Flow<List<QuestionAttemptEntity>>

    @Query("SELECT * FROM question_attempt ORDER BY answeredAtEpochMs DESC")
    fun observeAllAttempts(): Flow<List<QuestionAttemptEntity>>

    @Query("SELECT COUNT(DISTINCT questionId) FROM question_attempt qa JOIN daily_question q ON q.id = qa.questionId WHERE q.islandId = :islandId")
    suspend fun countAnsweredInIsland(islandId: String): Int

    @Query(
        """
        SELECT * FROM daily_question q
        WHERE q.id IN (SELECT DISTINCT questionId FROM question_attempt)
        ORDER BY RANDOM() LIMIT 1
        """
    )
    suspend fun pickAlreadyAnsweredForRevision(): DailyQuestionEntity?
}
