package com.example.musicapp.ui.song

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
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
import com.example.musicapp.ui.search.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class SongFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: SongFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = SongFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
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

    //brings back nav bar and current song bottom bar when exiting song fragment
    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().nav_view.visibility = View.VISIBLE
        requireActivity().currentSongBottomBar.visibility = View.VISIBLE
        _binding = null
    }
}