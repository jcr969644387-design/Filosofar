package com.educalab.filosofar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.educalab.filosofar.data.local.entity.PhilosophyIslandEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IslandDao {

    @Query("SELECT * FROM philosophy_island ORDER BY sortOrder ASC")
    fun observeAll(): Flow<List<PhilosophyIslandEntity>>

    @Query("SELECT * FROM philosophy_island WHERE id = :id LIMIT 1")
    suspend fun get(id: String): PhilosophyIslandEntity?

    @Query("SELECT COUNT(*) FROM philosophy_island")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(islands: List<PhilosophyIslandEntity>)
}
