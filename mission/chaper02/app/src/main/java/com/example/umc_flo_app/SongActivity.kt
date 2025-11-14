package com.example.umc_flo_app

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.umc_flo_app.databinding.ActivitySongBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongActivity : AppCompatActivity(){
    lateinit var binding: ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    lateinit var db: AppDatabase
    private var songs: List<Song> = arrayListOf()
    private var nowPos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!

        initSong()

        lifecycleScope.launch(Dispatchers.IO){
            songs = db.songDao().getSongs()
            findNowPos()
        }

        val uid = "asdfqwer1234"
        val userSongsRef = FirebaseDatabase.getInstance().getReference("songs").child(uid)

        userSongsRef.get().addOnSuccessListener { dataSnapshot ->
            val songList = arrayListOf<Song>()

            for(childSnapshot in dataSnapshot.children){
                val song = childSnapshot.getValue(Song::class.java)
                if(song != null){
                    songList.add(song)
                }
            }

            runOnUiThread {
                setPlayer(song)
            }
        }

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
            moveSong(-1)
        }

        binding.songBtnNextIv.setOnClickListener {
            moveSong(1)
        }

        binding.songBtnLikeIv.setOnClickListener {
            setLikeStatus()
        }

        binding.ivSongBtnLikeOn.setOnClickListener {
            setLikeStatus()
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
                songId = intent.getIntExtra("songId", 0),
                title = intent.getStringExtra("title")!!,
                singer = intent.getStringExtra("singer")!!,
                second = intent.getIntExtra("second", 0),
                playTime = intent.getIntExtra("playTime", 60),
                isPlaying = intent.getBooleanExtra("isPlaying", false),
                music = intent.getIntExtra("music", R.raw.music_lilac),
                coverImg = intent.getIntExtra("coverImg", R.drawable.img_album_exp2),
                isLike = intent.getBooleanExtra("isLike", false),
                albumIdx = intent.getIntExtra("albumIdx", 0)
            )
        }
    }

    private fun findNowPos(){
        nowPos = songs.indexOfFirst{ it.songId == song.songId}
    }

    private fun setPlayer(song: Song){
        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer
        binding.songAlbumImgIv.setImageResource(song.coverImg)

        if(song.isLike == false){
            binding.ivSongBtnLikeOn.visibility = View.GONE
            binding.songBtnLikeIv.visibility = View.VISIBLE
        }
        else{
            binding.ivSongBtnLikeOn.visibility = View.VISIBLE
            binding.songBtnLikeIv.visibility = View.GONE
        }

        if(mediaPlayer != null){
            mediaPlayer?.release()
            mediaPlayer = null
        }

        mediaPlayer = MediaPlayer.create(this, song.music)

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

    fun setLikeStatus(){
        song.isLike = !song.isLike

        if(song.isLike == false){
            binding.ivSongBtnLikeOn.visibility = View.GONE
            binding.songBtnLikeIv.visibility = View.VISIBLE
        }
        else{
            binding.ivSongBtnLikeOn.visibility = View.VISIBLE
            binding.songBtnLikeIv.visibility = View.GONE
        }

        lifecycleScope.launch(Dispatchers.IO) {
            db.songDao().update(song)

            val uid = "asdfqwer1234"
            val songRef = FirebaseDatabase.getInstance().getReference("songs")

            songRef.child(uid).child(song.songId.toString()).child("like").setValue(song.isLike)
        }
    }

    private fun startTimer(){
        if(::timer.isInitialized && timer.isAlive){
            timer.interrupt()
        }
        timer = Timer(song.playTime, song.isPlaying, song.second * 1000)
        timer.start()
    }

    private fun moveSong(direction: Int) {
        if(songs.isEmpty()){
            return
        }

        nowPos += direction
        if(nowPos < 0){
            nowPos = songs.size - 1
        }
        else if(nowPos >= songs.size){
            nowPos = 0
        }

        song = songs[nowPos]

        setPlayer(song)
        setPlayerStatus(true)
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

        editor.putInt("songId", song.songId)
        editor.putInt("songSecond", song.second)


        editor.apply()
    }

    override fun onDestroy(){
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}