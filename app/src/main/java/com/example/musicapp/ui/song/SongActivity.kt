package com.example.musicapp.ui.song

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.example.musicapp.R
import com.example.musicapp.databinding.ActivitySongBinding
import kotlinx.android.synthetic.main.activity_main.*
import android.content.ComponentName
import android.content.Context

import android.widget.Toast

import com.example.musicapp.MainActivity

import android.os.IBinder

import android.content.ServiceConnection
import com.example.musicapp.ui.song.MediaPlayerService.LocalBinder
import android.content.Intent





class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding

    private lateinit var songViewModel: SongViewModel

    private var player: MediaPlayerService? = null //instance of service
    var serviceBound = false //checks if service bound or not

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        songViewModel =
            ViewModelProvider(this).get(SongViewModel::class.java)

        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //palette api to set background color
        val drawable = binding.songImage.drawable
        val bitmap = drawable.toBitmap()
        setBackgroundColor(bitmap)

        /**
         * TO DO: Navigate back from button
         */
        //back button action
        binding.backButtonSongFragment.setOnClickListener {

        }

        playAudio("https://upload.wikimedia.org/wikipedia/commons/6/6c/Grieg_Lyric_Pieces_Kobold.ogg")
    }

    //change background color of song view
    /**
     * (TO DO: check if dominant color already present in database, if not then look for it
     * and add in database)
     */
    private fun setBackgroundColor(bitmap: Bitmap) {
        Palette.Builder(bitmap).generate {
            it?.let { palette ->
                //getting vibrant color
                val dominantColor = palette.getVibrantColor(
                    ContextCompat.getColor(
                        this,
                        R.color.darkGray
                    )
                )
                val hexColor = "#" + Integer.toHexString(dominantColor).substring(2)

                //creating gradient background
                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(
                        Color.parseColor(hexColor),
                        Color.parseColor("#000000")
                    )
                )
                gradientDrawable.cornerRadius = 0f
                //Set Gradient
                binding.songFragmentLayout.background = gradientDrawable
            }
        }
    }

    private fun playAudio(media: String) {
        //Check is service is active
        if (!serviceBound) {
            val playerIntent = Intent(this, MediaPlayerService::class.java)
            playerIntent.putExtra("media", media)
            startService(playerIntent)
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    //Binding this Client to the AudioPlayer Service
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as LocalBinder
            player = binder.service
            serviceBound = true
            Toast.makeText(this@SongActivity, "Service Bound", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceBound = false
        }
    }
}