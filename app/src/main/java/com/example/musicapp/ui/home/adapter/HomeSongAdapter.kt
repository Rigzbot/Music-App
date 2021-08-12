package com.example.musicapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.data.song.Song
import com.example.musicapp.databinding.RvHomeSongBinding

class HomeSongAdapter(private val onItemClicked: (Song) -> Unit) :
    ListAdapter<Song, HomeSongAdapter.SongViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            RvHomeSongBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    /**
     * TO DO: image to take value from db rather than hard coded
     */
    class SongViewHolder(private var binding: RvHomeSongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.apply {
                songNameRVHome.text = song.songName
                singerRVHome.text = song.singerName
                songImageRVHome.setImageResource(R.drawable.album_art_sample)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Song>() {
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem.songName == newItem.songName
            }

        }
    }
}