package com.example.umc_flo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_flo_app.databinding.FragmentAlbumSongBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumSongFragment : Fragment() {
    lateinit var binding: FragmentAlbumSongBinding
    private var albumId: Int = 1

    companion object {
        fun newInstance(albumId: Int): AlbumSongFragment {
            val fragment = AlbumSongFragment()
            val args = Bundle()
            args.putInt("albumId", albumId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt("albumId", 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumSongBinding.inflate(inflater, container, false)

        val db = AppDatabase.getInstance(requireContext())!!

        lifecycleScope.launch(Dispatchers.IO){
            val songs = db.songDao().getSongsInAlbum(albumId)

            withContext(Dispatchers.Main){
                val albumSongRVAdapter = AlbumSongRVAdapter(songs)
                binding.rvAlbumSong.adapter = albumSongRVAdapter
                binding.rvAlbumSong.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBtnToggleOff.setOnClickListener {
            binding.ivBtnToggleOff.isSelected = !binding.ivBtnToggleOff.isSelected
        }
    }

}