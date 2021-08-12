package com.example.musicapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.MusicApplication
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentHomeBinding
import com.example.musicapp.ui.home.adapter.HomeSongAdapter
import kotlinx.android.synthetic.main.activity_main.*

class HomeFragment : Fragment() {

    //initialise viewModel
    private val viewModel: HomeViewModel by activityViewModels {
        HomeViewModelFactory(
            (activity?.application as MusicApplication).database.songDao()
        )
    }

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * TO DO: Update to display playlists as well and go to playlist screen on click
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setting up song adapter
        val songAdapter = HomeSongAdapter {
            findNavController().navigate(R.id.action_navigation_home_to_songFragment)
        }

        //setting recent song recyclerView
        binding.recentRecyclerView.adapter = songAdapter
        viewModel.recentItems.observe(this.viewLifecycleOwner) { songs ->
            songs.let {
                songAdapter.submitList(it)
            }
        }
        binding.recentRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}