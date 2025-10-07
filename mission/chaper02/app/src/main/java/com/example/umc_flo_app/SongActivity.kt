package com.example.umc_flo_app

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_flo_app.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity(){
    lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val singer = intent.getStringExtra("singer")

        binding.songTitleTv.text = title
        binding.songSingerTv.text = singer

        binding.songBtnDownIv.setOnClickListener {
            finish()
        }

        binding.songBtnPlayIv.setOnClickListener {
            Log.d("", "버튼이 클릭되었습니다!")
            setPlayerStatus(false)
        }

        binding.songBtnPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songBtnRandomInactiveIv.setOnClickListener {
            setRandomStatus()
        }

        binding.songBtnRepeatInactiveIv.setOnClickListener {
            setRepeatStatus()
        }
    }

    fun setPlayerStatus(isPlaying: Boolean){
        if(isPlaying){
            binding.songBtnPlayIv.visibility = View.VISIBLE
            binding.songBtnPauseIv.visibility = View.GONE
        }
        else{
            binding.songBtnPlayIv.visibility = View.GONE
            binding.songBtnPauseIv.visibility = View.VISIBLE
        }
    }

    fun setRandomStatus(){
        binding.songBtnRandomInactiveIv.isSelected = !binding.songBtnRandomInactiveIv.isSelected
    }

    fun setRepeatStatus(){
        binding.songBtnRepeatInactiveIv.isSelected = !binding.songBtnRepeatInactiveIv.isSelected
    }


}