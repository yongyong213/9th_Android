package com.example.umc_flo_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_flo_app.databinding.FragmentPannelBinding

class PannelFragment: Fragment() {

    lateinit var binding: FragmentPannelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPannelBinding.inflate(inflater, container, false)

        val bgImgResId = arguments?.getInt("backgroundImgResId") ?: 0
        val title = arguments?.getString("title")
        val albumImgResId = arguments?.getInt("albumImgResId") ?: 0
        val albumTitle = arguments?.getString("albumTitle")
        val singer = arguments?.getString("singer")

        binding.pannelBackgroundIv.setImageResource(bgImgResId)
        binding.pannelTitleTv.text = title
        binding.pannelAlbumImgIv.setImageResource(albumImgResId)
        binding.pannelAlbumTitleTv.text = albumTitle
        binding.pannelAlbumSingerTv.text = singer

        return binding.root
    }

    companion object {
        // PannelFragment 인스턴스를 생성하면서 데이터(pannel)를 전달하는 함수
        fun newInstance(pannel: Pannel): PannelFragment {
            val fragment = PannelFragment()
            val args = Bundle()
            args.putInt("backgroundImgResId", pannel.backgroundImgResId)
            args.putString("title", pannel.title)
            args.putInt("albumImgResId", pannel.albumImgResId)
            args.putString("albumTitle", pannel.albumTitle)
            args.putString("singer", pannel.singer)
            fragment.arguments = args
            return fragment
        }
    }
}