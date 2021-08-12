package com.example.musicapp.data.song

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * TO DO: Get image of album from database
 */
@Dao
interface SongDao {
    @Query("SELECT * FROM song ORDER BY lastPlayed Limit 5")
    fun getRecentSongs(): Flow<List<Song>>

    @Query("SELECT * FROM song WHERE id =:id")
    fun getSong(id: Int): Flow<Song>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(song: Song)
}