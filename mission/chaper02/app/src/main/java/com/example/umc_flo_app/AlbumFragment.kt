package com.example.umc_flo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.umc_flo_app.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    private val tabTitles = arrayListOf("수록곡", "상세정보", "영상")

    lateinit var binding: FragmentAlbumBinding
    private val args: AlbumFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val title = args.albumTitle
        val singer = args.albumSinger
        val album_img = args.albumImg

        binding.albumSongTitleTv.text = title
        binding.albumSongSingerTv.text = singer
        binding.albumSongCoverIv.setImageResource(album_img)

        binding.albumBtnBackIv.setOnClickListener {
            findNavController().navigateUp();
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = args.albumTitle
        val singer = args.albumSinger

        val AlbumAdapter = AlbumSongVPAdapter(this, title, singer)
        binding.vpAlbum.adapter = AlbumAdapter

        TabLayoutMediator(binding.tblAlbumFragment, binding.vpAlbum){tab, position -> tab.text=tabTitles[position]}.attach()
    }
}