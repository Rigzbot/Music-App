package com.example.musicapp.ui.home

import androidx.lifecycle.*
import com.example.musicapp.data.song.Song
import com.example.musicapp.data.song.SongDao

class HomeViewModel(private val songDao: SongDao) : ViewModel() {
    val recentItems: LiveData<List<Song>> = songDao.getRecentSongs().asLiveData()
}

class HomeViewModelFactory(private val songDao: SongDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(songDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}