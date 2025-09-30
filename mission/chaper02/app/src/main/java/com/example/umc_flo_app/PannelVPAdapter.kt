package com.example.umc_flo_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.umc_flo_app.databinding.FragmentPannelBinding

class PannelVPAdapter(fragment: Fragment, private val pannelList: List<Pannel>): FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = pannelList.size

    override fun createFragment(position: Int): Fragment {
        // PannelFragment의 newInstance 함수를 사용하여 데이터와 함께 프래그먼트 생성
        return PannelFragment.newInstance(pannelList[position])
    }

}