package com.example.umc_flo_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_flo_app.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private var albumDatas = ArrayList<Album>()
    private var pannelDatas = ArrayList<Pannel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        pannelDatas.clear()
        pannelDatas.apply {
            add(Pannel(R.drawable.img_first_album_default, "매혹적인 음색의 여성 보컬 \n팝", R.drawable.img_album_exp, "In My Bed", "Bear"))
            add(Pannel(R.drawable.img_album_exp4, "드라이브할 때 듣는\n감미로운 재즈 힙합", R.drawable.img_album_exp2, "LILAC", "아이유 (IU)"))
        }

        val pannelAdapter = PannelVPAdapter(this, pannelDatas)
        binding.vpHomePannel.adapter = pannelAdapter
        binding.vpHomePannel.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.homePannelIndicatorTl, binding.vpHomePannel) { tab, position ->
            // 여기는 아무것도 안해도 점(dot)으로 표시됩니다.
        }.attach()

        albumDatas.clear()
        albumDatas.apply {
            add(Album("Butter", "방탄소년단", R.drawable.img_album_exp))
            add(Album("LILAC", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "aespa", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연", R.drawable.img_album_exp6))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter

        // 레이아웃 매니저 설정
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album){
                val action = HomeFragmentDirections.actionHomeFragmentToAlbumFragment(
                    album.title,
                    album.singer,
                    album.coverImg
                )
                findNavController().navigate(action)
            }
        })



        return binding.root
    }
}