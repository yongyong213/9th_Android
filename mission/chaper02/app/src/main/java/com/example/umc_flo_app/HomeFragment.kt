package com.example.umc_flo_app

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import me.relex.circleindicator.CircleIndicator3
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private var albumDatas = ArrayList<Album>()
    private var pannelDatas = ArrayList<Pannel>()

    private val sliderHandler = Handler(Looper.getMainLooper())
    private val sliderRunnable: Runnable = Runnable{
        val viewPager = binding.vpHomePannel
        val nextItem = (viewPager.currentItem + 1) % pannelDatas.size

        viewPager.setCurrentItem(nextItem, true)

        sliderHandler.postDelayed(this.sliderRunnable, 3000)
    }

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

        //val pannelAdapter = PannelVPAdapter(this, pannelDatas)
        //binding.vpHomePannel.adapter = pannelAdapter
        //binding.vpHomePannel.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //binding.homePannelIndicatorTl.setViewPager2(binding.vpHomePannel)
        val pannelAdapter = PannelVPAdapter(this, pannelDatas)
        binding.vpHomePannel.adapter = pannelAdapter
        binding.vpHomePannel.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator: CircleIndicator3 = binding.homePannelIndicatorTl
        indicator.setViewPager(binding.vpHomePannel)

        albumDatas.clear()
        albumDatas.apply {
            add(Album(0, "Butter", "방탄소년단", R.drawable.img_album_exp, false))
            add(Album(0, "LILAC", "아이유 (IU)", R.drawable.img_album_exp2, false))
            add(Album(0, "Next Level", "aespa", R.drawable.img_album_exp3, false))
            add(Album(0, "Boy with Luv", "방탄소년단", R.drawable.img_album_exp4, false))
            add(Album(0, "BBoom BBoom", "모모랜드", R.drawable.img_album_exp5, false))
            add(Album(0, "Weekend", "태연", R.drawable.img_album_exp6, false))
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

            override fun onPlayButtonClick(album: Album) {
                (activity as MainActivity).updateMiniPlayer(album)
            }
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.removeCallbacks(sliderRunnable)
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }
}