package com.example.umc_flo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_flo_app.databinding.FragmentSavedSongBinding

class SaveSongFragment : Fragment(), LockerRVAdapter.OnItemClickListener {

    lateinit var binding: FragmentSavedSongBinding

    private val LockerSongDatas = ArrayList<SaveSongData>()
    var LockerRVAdapter = LockerRVAdapter(LockerSongDatas)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container,false)

        LockerSongDatas.apply{
            add(SaveSongData("Butter", "방탄소년단", R.drawable.img_album_exp))
            add(SaveSongData("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(SaveSongData("Boy with Luv", "방탄소년단", R.drawable.img_album_exp4))
            add(SaveSongData("Next Level", "aespa", R.drawable.img_album_exp3))
            add(SaveSongData("BBoom BBoom", "모모랜드", R.drawable.img_album_exp5))
            add(SaveSongData("Weekend", "태연", R.drawable.img_album_exp6))
            add(SaveSongData("Butter", "방탄소년단", R.drawable.img_album_exp))
            add(SaveSongData("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(SaveSongData("Boy with Luv", "방탄소년단", R.drawable.img_album_exp4))
            add(SaveSongData("Next Level", "aespa", R.drawable.img_album_exp3))
            add(SaveSongData("BBoom BBoom", "모모랜드", R.drawable.img_album_exp5))
            add(SaveSongData("Weekend", "태연", R.drawable.img_album_exp6))
            add(SaveSongData("Butter", "방탄소년단", R.drawable.img_album_exp))
            add(SaveSongData("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(SaveSongData("Boy with Luv", "방탄소년단", R.drawable.img_album_exp4))
            add(SaveSongData("Next Level", "aespa", R.drawable.img_album_exp3))
            add(SaveSongData("BBoom BBoom", "모모랜드", R.drawable.img_album_exp5))
            add(SaveSongData("Weekend", "태연", R.drawable.img_album_exp6))
        }

        LockerRVAdapter = LockerRVAdapter(LockerSongDatas)
        binding.rvLockerSong.adapter=LockerRVAdapter
        binding.rvLockerSong.layoutManager = LinearLayoutManager(context)

        LockerRVAdapter.setOnItemClickListener(this)
        return binding.root
    }

    override fun onMoreButtonClick(position: Int) {
        LockerRVAdapter.removeItem(position)
    }

}