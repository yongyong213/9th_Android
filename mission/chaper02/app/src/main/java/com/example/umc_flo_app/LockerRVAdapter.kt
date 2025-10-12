package com.example.umc_flo_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_flo_app.AlbumSongRVAdapter.ViewHolder
import com.example.umc_flo_app.databinding.ItemLockerSongBinding

class LockerRVAdapter(private val songList: ArrayList<SaveSongData>): RecyclerView.Adapter<LockerRVAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onMoreButtonClick(position: Int)
    }
    private lateinit var mItemClickListener: OnItemClickListener

    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun removeItem(position: Int){
        songList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemLockerSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(songList[position])
    }


    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(val binding: ItemLockerSongBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(song: SaveSongData){
            binding.tvItemLockerSongTitle.text = song.title
            binding.tvItemLockerSongSinger.text = song.singer
            binding.ivItemLockerSongImg.setImageResource(song.songImage)

            binding.ivBtnMore.setOnClickListener {
                mItemClickListener.onMoreButtonClick(bindingAdapterPosition)
            }
        }
    }
}