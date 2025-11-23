package com.example.umc_flo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.umc_flo_app.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumFragment : Fragment() {
    private val tabTitles = arrayListOf("수록곡", "상세정보", "영상")

    lateinit var binding: FragmentAlbumBinding
    private val gson: Gson = Gson()
    private var album: Album? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumJson = arguments?.getString("album")
        if (albumJson != null) {
            album = gson.fromJson(albumJson, Album::class.java)
        }

        album?.let{
            setInit(it)
        }

        binding.albumBtnBackIv.setOnClickListener {
            findNavController().navigateUp();
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        album?.let{ safeAlbum ->
            val AlbumAdapter = AlbumSongVPAdapter(this, safeAlbum.albumId)
            binding.vpAlbum.adapter = AlbumAdapter
            TabLayoutMediator(binding.tblAlbumFragment, binding.vpAlbum) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    private fun setInit(album: Album) {
        binding.albumSongTitleTv.text = album.title
        binding.albumSongSingerTv.text = album.singer
        binding.albumSongCoverIv.setImageResource(album.coverImg)

        if (album.islike) {
            binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }

        binding.albumBtnLikeOffIv.setOnClickListener {
            setLike(album)
        }
    }

    private fun setLike(album: Album) {
        val User = FirebaseAuth.getInstance().currentUser

        if (User == null) {
            Toast.makeText(context, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        album.islike = !album.islike

        if (album.islike) {
            binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }

        val db = AppDatabase.getInstance(requireContext())!!
        lifecycleScope.launch(Dispatchers.IO) {
            db.albumDao().update(album)
        }

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            updateFirebase(user.uid, album)
        }
    }

    private fun updateFirebase(userId: String, album: Album) {
        val ref = Firebase.database.getReference("users")
            .child(userId)
            .child("likedAlbums")
            .child(album.albumId.toString())

        if (album.islike) {
            ref.setValue(album)
        } else {
            ref.removeValue()
        }
    }
}