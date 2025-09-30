package com.example.umc_flo_app

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> SavedSongFragment()
            1 -> SongFileFragment()
            else -> SaveAlbumFragment()
        }
    }

    override fun getItemCount(): Int = 3

}