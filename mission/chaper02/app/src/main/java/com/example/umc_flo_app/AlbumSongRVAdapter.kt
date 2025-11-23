package com.example.umc_flo_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_flo_app.databinding.ItemAlbumSongBinding

class AlbumSongRVAdapter(private val songs: List<Song>): RecyclerView.Adapter<AlbumSongRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAlbumSongBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            val song = songs[position]

            binding.tvItemAlbumSongTitle.text = song.title
            binding.tvItemAlbumSongSinger.text = song.singer
            binding.tvItemAlbumSongNum.text = (position+1).toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemAlbumSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = songs.size
}