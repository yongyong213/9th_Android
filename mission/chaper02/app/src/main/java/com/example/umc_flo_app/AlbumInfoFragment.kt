package com.example.umc_flo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.umc_flo_app.databinding.FragmentAlbumInfoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumInfoFragment : Fragment() {

    lateinit var  binding: FragmentAlbumInfoBinding
    lateinit var db: AppDatabase
    private var albumId: Int = 1
    companion object{
        private const val ARG_ALBUM_ID = "albumId"

        fun newInstance(albumId: Int): AlbumInfoFragment{
            val fragment = AlbumInfoFragment()
            val args = Bundle()
            args.putInt(ARG_ALBUM_ID, albumId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt(ARG_ALBUM_ID, 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumInfoBinding.inflate(inflater, container, false)

        db = AppDatabase.getInstance(requireContext())!!

        lifecycleScope.launch(Dispatchers.IO){
            val album = db.albumDao().getAlbum(albumId)

            withContext(Dispatchers.Main){
                album?.let{
                    binding.tvAlbumInfoDescription.text = getString(R.string.album_info_description, it.title, it.singer)
                }
            }
        }

        return binding.root
    }
}