package com.example.umc_flo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.umc_flo_app.databinding.FragmentAlbumInfoBinding

class AlbumInfoFragment : Fragment() {

    lateinit var  binding: FragmentAlbumInfoBinding

    companion object{
        private const val ARG_TITLE = "title"
        private const val ARG_SINGER = "singer"

        fun newInstance(title: String, singer: String): AlbumInfoFragment{
            val fragment = AlbumInfoFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_SINGER, singer)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumInfoBinding.inflate(inflater, container, false)

        val title = arguments?.getString(ARG_TITLE)
        val singer = arguments?.getString(ARG_SINGER)

        binding.tvAlbumInfoDescription.text = getString(R.string.album_info_description, title, singer)

        return binding.root
    }
}