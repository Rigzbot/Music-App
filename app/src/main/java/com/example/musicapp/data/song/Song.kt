package com.example.musicapp.data.song

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Entity data class represents a single row in the database.
 */

/**
 * TO DO: Implement storing image of album
 */
@Entity
data class Song(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val songName: String = "",
    @ColumnInfo(name = "singer")
    val singerName: String = "",
    @ColumnInfo(name = "length")
    val songLength: Double = 0.00,
    @ColumnInfo(name = "genre")
    val genre: String = "",
    @ColumnInfo(name="imagePath")
    val imagePath: String = "",
    @Nullable @ColumnInfo(name = "imageColor")
    val imageColor: String = "",
    @Nullable @ColumnInfo(name = "lastPlayed")
    val lastPlayed: String = ""
)

/**
 * TO DO: get current date and time in string format
 */