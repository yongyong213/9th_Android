package com.example.umc_flo_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_flo_app.databinding.ItemLockerAlbumBinding

class SaveAlbumRVAdapter(private val albumList: ArrayList<SaveAlbumData>): RecyclerView.Adapter<SaveAlbumRVAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onMoreButtonClick(position: Int)
        fun onPlayButtonClick(position: Int)
    }

    private lateinit var mItemClickListener: OnItemClickListener

    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemLockerAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(albumList[position])
    }


    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemLockerAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: SaveAlbumData){
            binding.tvItemAlbumLockerTitle.text = album.title
            binding.tvItemAlbumLockerSinger.text = album.singer
            binding.tvItemAlbumLockerInfo.text = album.info
            binding.ivItemAlbumLockerCover.setImageResource(album.coverImgRes)

            if (album.isPlaying) {
                binding.ivBtnPlay.setImageResource(R.drawable.btn_miniplay_pause) // 멈춤 아이콘
            } else {
                binding.ivBtnPlay.setImageResource(R.drawable.btn_player_play)  // 재생 아이콘
            }


            binding.ivBtnAlbumLockerMore.setOnClickListener {
                mItemClickListener.onMoreButtonClick(bindingAdapterPosition)
            }

            binding.ivBtnPlay.setOnClickListener {
                mItemClickListener.onMoreButtonClick(bindingAdapterPosition)
            }

            binding.ivBtnPlay.setOnClickListener {
                mItemClickListener.onPlayButtonClick(bindingAdapterPosition)
            }
        }
    }
}