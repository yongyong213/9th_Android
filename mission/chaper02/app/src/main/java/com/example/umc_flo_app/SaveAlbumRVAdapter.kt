package com.example.umc_flo_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_flo_app.databinding.ItemLockerAlbumBinding

class SaveAlbumRVAdapter(private val albums: ArrayList<Album>): RecyclerView.Adapter<SaveAlbumRVAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onMoreButtonClick(position: Int)
        fun onPlayButtonClick(position: Int)
    }

    private lateinit var mItemClickListener: OnItemClickListener

    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun setData(newAlbums: List<Album>) {
        albums.clear()
        albums.addAll(newAlbums)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        albums.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, albums.size)
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
        holder.bind(albums[position])
    }


    override fun getItemCount(): Int = albums.size

    inner class ViewHolder(val binding: ItemLockerAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.tvItemAlbumLockerTitle.text = album.title
            binding.tvItemAlbumLockerSinger.text = album.singer
//            binding.tvItemAlbumLockerInfo.text = album.info
            binding.ivItemAlbumLockerCover.setImageResource(album.coverImg)

            binding.ivBtnPlay.setImageResource(R.drawable.btn_player_play)

            binding.ivBtnAlbumLockerMore.setOnClickListener {
                mItemClickListener.onMoreButtonClick(bindingAdapterPosition)
            }

            binding.ivBtnPlay.setOnClickListener {
                mItemClickListener.onPlayButtonClick(bindingAdapterPosition)
            }
        }
    }
}