package com.example.umc_flo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_flo_app.databinding.FragmentSaveAlbumBinding

class SaveAlbumFragment : Fragment(), SaveAlbumRVAdapter.OnItemClickListener {

    lateinit var binding: FragmentSaveAlbumBinding

    private val lockerAlbumDatas = ArrayList<SaveAlbumData>()

    var SaveAlbumRVAdapter = SaveAlbumRVAdapter(lockerAlbumDatas)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveAlbumBinding.inflate(inflater, container, false)

        if(lockerAlbumDatas.isEmpty()){
            lockerAlbumDatas.apply {
                add(SaveAlbumData("라일락", "아이유", "2025.11.1 | 정규 | kpop", R.drawable.img_album_exp2))
                add(SaveAlbumData("라일락", "아이유", "2025.11.1 | 정규 | kpop", R.drawable.img_album_exp2))
            }
        }


        SaveAlbumRVAdapter = SaveAlbumRVAdapter(lockerAlbumDatas)
        binding.rvLockerAlbum.adapter=SaveAlbumRVAdapter
        binding.rvLockerAlbum.layoutManager = LinearLayoutManager(context)

        SaveAlbumRVAdapter.setOnItemClickListener(this)

        return binding.root
    }

    override fun onMoreButtonClick(position: Int) {
        SaveAlbumRVAdapter.removeItem(position)
    }

    override fun onPlayButtonClick(position: Int) {
        val album = lockerAlbumDatas[position]
        album.isPlaying = !album.isPlaying

        SaveAlbumRVAdapter.notifyItemChanged(position)
    }

}