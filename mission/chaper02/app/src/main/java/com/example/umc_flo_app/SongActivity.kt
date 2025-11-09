package com.example.umc_flo_app

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_flo_app.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity(){
    lateinit var binding: ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

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

        binding.sbSongProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean){
                if(fromUser){
                    timer.mills = progress.toFloat()
                    timer.second = progress / 1000
                    binding.songCurrentTimeTv.text = String.format("%02d:%02d", timer.second / 60, timer.second % 60)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?){}

            override fun onStopTrackingTouch(seekBar: SeekBar?){
                seekBar?.let {
                    mediaPlayer?.seekTo(it.progress)
                    timer.second = it.progress / 1000
                    binding.songCurrentTimeTv.text = String.format("%02d:%02d", timer.second / 60, timer.second % 60)
                }
            }
        })

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
        mediaPlayer?.seekTo(song.second * 1000)

        binding.songCurrentTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)

        startTimer()

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
        timer = Timer(song.playTime, song.isPlaying, song.second * 1000)
        timer.start()
    }

    private fun restartMusic() {

        mediaPlayer?.seekTo(0)

        binding.sbSongProgress.progress = 0
        binding.songCurrentTimeTv.text = "00:00"

        timer.mills = 0f
        timer.second = 0

        if (song.isPlaying) {
            mediaPlayer?.start()
        }
    }


    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true, startMS: Int = 0):Thread(){

        var second : Int = startMS / 1000
        var mills: Float = startMS.toFloat()

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
                            binding.sbSongProgress.progress = mills.toInt()
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

    override fun onPause(){
        super.onPause()
        setPlayerStatus(false)
        song.second = (binding.sbSongProgress.progress / 1000)
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val songJson = gson.toJson(song)
        editor.putString("song", songJson)

        editor.apply()
    }

    override fun onDestroy(){
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}