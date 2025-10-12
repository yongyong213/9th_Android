package com.example.umc_flo_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_flo_app.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onPlayButtonClick(album: Album)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(albumList[position])
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumImgIv.setImageResource(album.coverImg)

            binding.itemAlbumImgIv.setOnClickListener {
                mItemClickListener.onItemClick(album)
            }

            binding.ivBtnPlay.setOnClickListener {
                mItemClickListener.onPlayButtonClick(album)
            }
        }
    }
}