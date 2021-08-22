package com.example.musicapp

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicapp.databinding.ActivityMainBinding

import android.content.Intent
import com.example.musicapp.ui.song.SongActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        //setting music name in bottom bar
        binding.currentSongBottomBar.isSelected = true

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        //up button navigates to songView when active
        binding.arrowUpBottomBar.setOnClickListener {
            moveToNewActivity()

            //close menu and nav bar after going to music screen
            binding.currentSongBottomBar.visibility = View.GONE
            binding.navView.visibility = View.GONE
        }
    }

    override fun onRestart() {
        super.onRestart()
        //open menu and nav bar after going to music screen
        binding.currentSongBottomBar.visibility = View.VISIBLE
        binding.navView.visibility = View.VISIBLE
    }

    private fun moveToNewActivity() {
        val intent = Intent(this, SongActivity::class.java)
        startActivity(intent)
    }
}