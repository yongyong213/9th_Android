package com.example.umc_flo_app

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumSongVPAdapter(
    fragment: Fragment,
    private val albumId: Int):
    FragmentStateAdapter(fragment){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> AlbumSongFragment.newInstance(albumId)
            1-> AlbumInfoFragment.newInstance(albumId)
            else-> AlbumVideoFragment()
        }
    }

    override fun getItemCount(): Int = 3
}