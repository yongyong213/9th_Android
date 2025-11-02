package com.example.umc_flo_app

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_flo_app.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity(){
    lateinit var binding: ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.songBtnDownIv.setOnClickListener {
            finish()
        }

        binding.songBtnPlayIv.setOnClickListener {
            Log.d("", "버튼이 클릭되었습니다!")
            setPlayerStatus(true)
        }

        binding.songBtnPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songBtnRandomInactiveIv.setOnClickListener {
            setRandomStatus()
        }

        binding.songBtnRepeatInactiveIv.setOnClickListener {
            setRepeatStatus()
        }

        binding.songBtnPreviousIv.setOnClickListener {
            restartMusic()
        }

        binding.songBtnNextIv.setOnClickListener {
            restartMusic()
        }

    }

    override fun onDestroy(){
        super.onDestroy()
        timer.interrupt()
    }
    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 60),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        val actualDurationMs = mediaPlayer?.duration ?: 0
        song.playTime = actualDurationMs / 1000
        binding.sbSongProgress.max = actualDurationMs
        binding.sbSongProgress.progress = song.second * 1000

        binding.songCurrentTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)

        setPlayerStatus(song.isPlaying)

    }

    fun setPlayerStatus(isPlaying: Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songBtnPlayIv.visibility = View.GONE
            binding.songBtnPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else{
            binding.songBtnPlayIv.visibility = View.VISIBLE
            binding.songBtnPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying==true){
                mediaPlayer?.pause()
            }
        }
    }

    fun setRandomStatus(){
        binding.songBtnRandomInactiveIv.isSelected = !binding.songBtnRandomInactiveIv.isSelected
    }

    fun setRepeatStatus(){
        binding.songBtnRepeatInactiveIv.isSelected = !binding.songBtnRepeatInactiveIv.isSelected
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    private fun restartMusic() {

        mediaPlayer?.seekTo(0)

        binding.sbSongProgress.progress = 0
        binding.songCurrentTimeTv.text = "00:00"

        if (song.isPlaying) {
            mediaPlayer?.start()
        }
    }


    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        override fun run(){
            super.run()
            try{
                while(true){

                    if(second >= playTime){
                        break
                    }

                    if(isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.sbSongProgress.progress = ((mills / playTime)*100).toInt()
                        }

                        if(mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songCurrentTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }

}