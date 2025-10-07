package com.example.umc_flo_app

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumSongVPAdapter(
    fragment: Fragment,
    private val title: String,
    private val singer: String):
    FragmentStateAdapter(fragment){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> AlbumSongFragment()
            1-> AlbumInfoFragment.newInstance(title, singer)
            else-> AlbumVideoFragment()
        }
    }

    override fun getItemCount(): Int = 3
}