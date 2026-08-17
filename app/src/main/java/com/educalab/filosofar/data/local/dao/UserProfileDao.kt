package com.educalab.filosofar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.educalab.filosofar.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM user_profile WHERE id = :id LIMIT 1")
    fun observe(id: Int = UserProfileEntity.SINGLE_ID): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = :id LIMIT 1")
    suspend fun get(id: Int = UserProfileEntity.SINGLE_ID): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(profile: UserProfileEntity)

    @Update
    suspend fun update(profile: UserProfileEntity)

    @Query("UPDATE user_profile SET onboardingCompleted = 1 WHERE id = :id")
    suspend fun markOnboardingComplete(id: Int = UserProfileEntity.SINGLE_ID)

    @Query("UPDATE user_profile SET soundEnabled = :enabled WHERE id = :id")
    suspend fun setSoundEnabled(enabled: Boolean, id: Int = UserProfileEntity.SINGLE_ID)

    @Query("UPDATE user_profile SET hapticsEnabled = :enabled WHERE id = :id")
    suspend fun setHapticsEnabled(enabled: Boolean, id: Int = UserProfileEntity.SINGLE_ID)

    @Query("UPDATE user_profile SET lastOpenedEpochMs = :timestamp WHERE id = :id")
    suspend fun touchLastOpened(timestamp: Long, id: Int = UserProfileEntity.SINGLE_ID)
}
