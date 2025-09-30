package com.example.umc_flo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.umc_flo_app.databinding.FragmentAlbumSongBinding

class AlbumSongFragment : Fragment() {
    lateinit var binding: FragmentAlbumSongBinding
    private val albumSongDatas = ArrayList<AlbumSong>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumSongBinding.inflate(inflater, container, false)

        albumSongDatas.apply{
            add(AlbumSong("사랑하게 될거야", "한로로"))
            add(AlbumSong("비틀비틀 짝짜꿍", "한로로"))
            add(AlbumSong("입춘", "한로로"))
            add(AlbumSong("자처", "한로로"))
        }

        val albumSongRVAdapter = AlbumSongRVAdapter(albumSongDatas)
        binding.rvAlbumSong.adapter = albumSongRVAdapter
        // Inflate the layout for this fragment
        return binding.root
    }

}