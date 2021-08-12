package com.example.musicapp

import android.app.Application
import com.example.musicapp.data.song.SongRoomDatabase

class MusicApplication: Application() {
    val database: SongRoomDatabase by lazy {SongRoomDatabase.getDatabase(this)}
}