package com.example.umc_flo_app

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumSongVPAdapter(fragment: Fragment): FragmentStateAdapter(fragment){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> AlbumSongFragment()
            1-> AlbumInfoFragment()
            else-> AlbumVideoFragment()
        }
    }

    override fun getItemCount(): Int = 3
}