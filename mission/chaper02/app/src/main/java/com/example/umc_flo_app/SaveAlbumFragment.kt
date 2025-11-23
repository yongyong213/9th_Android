package com.example.umc_flo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_flo_app.databinding.FragmentSaveAlbumBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveAlbumFragment : Fragment(){

    lateinit var binding: FragmentSaveAlbumBinding
    lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveAlbumBinding.inflate(inflater, container, false)
        db = AppDatabase.getInstance(requireContext())!!

//        if(lockerAlbumDatas.isEmpty()){
//            lockerAlbumDatas.apply {
//                add(SaveAlbumData("라일락", "아이유", "2025.11.1 | 정규 | kpop", R.drawable.img_album_exp2))
//                add(SaveAlbumData("라일락", "아이유", "2025.11.1 | 정규 | kpop", R.drawable.img_album_exp2))
//            }
//        }
//
//
//        SaveAlbumRVAdapter = SaveAlbumRVAdapter(lockerAlbumDatas)
//        binding.rvLockerAlbum.adapter=SaveAlbumRVAdapter
//        binding.rvLockerAlbum.layoutManager = LinearLayoutManager(context)
//
//        SaveAlbumRVAdapter.setOnItemClickListener(this)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview() {
        lifecycleScope.launch(Dispatchers.IO) {
            val likedAlbums = db.albumDao().getLikedAlbums()

            withContext(Dispatchers.Main) {
                val albumList = ArrayList(likedAlbums)

                val adapter = SaveAlbumRVAdapter(albumList)
                binding.rvLockerAlbum.adapter = adapter
                binding.rvLockerAlbum.layoutManager = LinearLayoutManager(context)

                adapter.setOnItemClickListener(object : SaveAlbumRVAdapter.OnItemClickListener {
                    override fun onMoreButtonClick(position: Int) {
                        // 삭제 로직 실행 (DB + 화면)
                        deleteAlbum(albumList[position], adapter, position)
                    }

                    override fun onPlayButtonClick(position: Int) {
                    }
                })
            }
        }
    }

    private fun deleteAlbum(album: Album, adapter: SaveAlbumRVAdapter, position: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            album.islike = false
            db.albumDao().update(album)

            withContext(Dispatchers.Main) {
                adapter.removeItem(position)
            }
        }
    }
}