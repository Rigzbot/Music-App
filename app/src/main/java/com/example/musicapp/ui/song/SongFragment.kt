package com.example.musicapp.ui.song

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import com.example.musicapp.R
import com.example.musicapp.databinding.SongFragmentBinding
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class SongFragment : Fragment() {
    //runnable object and handler to handle slider position
    private lateinit var runnable: Runnable
    private var handler = Handler()

    private lateinit var songViewModel: SongViewModel

    private lateinit var binding: SongFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        songViewModel =
            ViewModelProvider(this).get(SongViewModel::class.java)

        binding = SongFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //palette api to set background color
        val drawable = binding.songImage.drawable
        val bitmap = drawable.toBitmap()
        setBackgroundColor(bitmap)

        //back button action
        binding.backButtonSongFragment.setOnClickListener {
            findNavController().navigateUp() //remove last stack
        }

        //media player, button, slider settings
        createMediaPLayer()
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
                        requireContext(),
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

    /**
     * Media player implementation
     */
    private fun createMediaPLayer() {
        val mediaPlayer = MediaPlayer.create(requireActivity(), R.raw.music)

        binding.slider.valueFrom = 0.0F
        //duration of song settings
        var duration = mediaPlayer.duration // in milliseconds

        //set value of song length
        binding.songLength.text = timeInMinutes(duration.toLong())

        //+1000 to allow slider value to go back to zero upon completion
        duration += 1000
        binding.slider.valueTo = duration.toFloat()

        //pause button settings
        binding.pauseButton.setOnClickListener {
            //check if media player is playing
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                binding.pauseButton.setImageResource(R.drawable.ic_pause)
            } else {
                //if the media player is already running
                mediaPlayer.pause()
                binding.pauseButton.setImageResource(R.drawable.ic_play)
            }
        }

        //change song progress according to slider position
        binding.slider.addOnChangeListener(Slider.OnChangeListener { _, value, fromUser ->
            if (fromUser) {
                mediaPlayer.seekTo(value.toInt())

                //set value of current text view
                binding.currentValueSong.text = timeInMinutes(value.toLong())
            }
        })

        //change position of slider according to position of song
        runnable = Runnable {
            val current = mediaPlayer.currentPosition
            binding.slider.value = current.toFloat()
            binding.currentValueSong.text = timeInMinutes(current.toLong())

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        /**
         * TO DO: set music player to play next song when current song finishes
         */
        mediaPlayer.setOnCompletionListener {
            binding.pauseButton.setImageResource(R.drawable.ic_play)
            binding.slider.value = 0.0F
        }
    }

    private fun timeInMinutes(duration: Long): String {
        return if (TimeUnit.MILLISECONDS.toMinutes(duration) <= 9) {
            String.format(
                "%01d: %02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            )
        } else {
            String.format(
                "%02d: %02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            )
        }
    }

    //brings back nav bar and current song bottom bar when exiting song fragment
    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().nav_view.visibility = View.VISIBLE
        requireActivity().currentSongBottomBar.visibility = View.VISIBLE
    }
}