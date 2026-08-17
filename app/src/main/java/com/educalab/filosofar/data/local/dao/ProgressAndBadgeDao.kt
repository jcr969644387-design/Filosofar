package com.educalab.filosofar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.educalab.filosofar.data.local.entity.BadgeEntity
import com.educalab.filosofar.data.local.entity.IslandProgressEntity
import com.educalab.filosofar.data.local.entity.UserBadgeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM island_progress ORDER BY islandId ASC")
    fun observeAll(): Flow<List<IslandProgressEntity>>

    @Query("SELECT * FROM island_progress WHERE islandId = :islandId LIMIT 1")
    suspend fun get(islandId: String): IslandProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(progress: IslandProgressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(progress: List<IslandProgressEntity>)

    @Query("SELECT COUNT(*) FROM island_progress")
    suspend fun count(): Int

    @Query("SELECT COALESCE(SUM(crystalsEarned), 0) FROM island_progress")
    fun observeTotalCrystals(): Flow<Int>
}

@Dao
interface BadgeDao {
    @Query("SELECT * FROM badge ORDER BY sortOrder ASC")
    fun observeAll(): Flow<List<BadgeEntity>>

    @Query("SELECT COUNT(*) FROM badge")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(badges: List<BadgeEntity>)

    @Query("SELECT * FROM user_badge")
    fun observeUnlocked(): Flow<List<UserBadgeEntity>>

    @Query("SELECT badgeId FROM user_badge")
    suspend fun unlockedIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun unlock(userBadge: UserBadgeEntity): Long
}
