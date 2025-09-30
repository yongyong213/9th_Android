package com.example.umc_flo_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_flo_app.databinding.ItemAlbumBinding
import com.example.umc_flo_app.databinding.ItemAlbumSongBinding

class AlbumSongRVAdapter(private val albumSongList: ArrayList<AlbumSong>): RecyclerView.Adapter<AlbumSongRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAlbumSongBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(albumSong: AlbumSong, position: Int){
            binding.tvItemAlbumSongTitle.text = albumSong.title
            binding.tvItemAlbumSongSinger.text = albumSong.singer
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
        holder.bind(albumSongList[position], position)
    }

    override fun getItemCount(): Int = albumSongList.size
}