package com.example.umc_flo_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.umc_flo_app.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var db: AppDatabase

    private lateinit var song: Song
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gson = Gson()

        db = AppDatabase.getInstance(applicationContext)
        initDb()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        binding.mainBnv.setupWithNavController(navController)

        binding.mainMiniplayerCl.setOnClickListener{
            val intent =Intent(this, SongActivity::class.java)

            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
            intent.putExtra("coverImg", song.coverImg)
            intent.putExtra("isLike", song.isLike)
            intent.putExtra("albumIdx", song.albumIdx)

            startActivity(intent)
        }
    }

    private fun initDb(){
        lifecycleScope.launch(Dispatchers.IO){
            val songDao = db.songDao()
            val albumDao = db.albumDao()

            if(albumDao.getAlbums().isEmpty()){
                val uid = "asdfqwer1234"
                val albumUid = "asdf9876";

                val songRef = FirebaseDatabase.getInstance().getReference("songs")
                val albumRef = FirebaseDatabase.getInstance().getReference("albums")

                val newAlbum1 = Album(
                    title = "LILAC",
                    singer = "아이유(IU)",
                    coverImg = R.drawable.img_album_exp2,
                    islike = false
                )
                val AlbumLILACId = albumDao.insert(newAlbum1).toInt()

                albumRef.child(albumUid).child("1").setValue(newAlbum1)

                val song1 = Song(
                    title = "라일락",
                    singer = "아이유(IU)",
                    second = 0,
                    playTime = 200,
                    isPlaying = false,
                    music = R.raw.music_lilac,
                    coverImg = R.drawable.img_album_exp2,
                    isLike = false,
                    albumIdx = AlbumLILACId,
                    songId = 1
                )

                val song2 = Song(
                    title = "Celebrity",
                    singer = "아이유(IU)",
                    second = 0,
                    playTime = 200,
                    isPlaying = false,
                    music = R.raw.music_celebrity,
                    coverImg = R.drawable.img_album_exp2,
                    isLike = false,
                    albumIdx = AlbumLILACId,
                    songId = 2
                )

                songRef.child(uid).child("1").setValue(song1)
                songRef.child(uid).child("2").setValue(song2)

                songDao.insert(song1)
                songDao.insert(song2)

                val newAlbum2 = Album(
                    title = "TEAM_BABY",
                    singer = "검정치마",
                    coverImg = R.drawable.img_album_exp8,
                    islike = false
                )
                val AlbumTEAMBABYId = albumDao.insert(newAlbum2).toInt()

                albumRef.child(albumUid).child("2").setValue(newAlbum2)

                val song3 = Song(
                    title = "Love Is All",
                    singer = "검정치마",
                    second = 0,
                    playTime = 200,
                    isPlaying = false,
                    music = R.raw.music_love_is_all,
                    coverImg = R.drawable.img_album_exp8,
                    isLike = false,
                    albumIdx = AlbumTEAMBABYId,
                )
                val song4 = Song(
                    title = "한시 오분 (1:05)",
                    singer = "검정치마",
                    second = 0,
                    playTime = 200,
                    isPlaying = false,
                    music = R.raw.music_1_05,
                    coverImg = R.drawable.img_album_exp8,
                    isLike = false,
                    albumIdx = AlbumTEAMBABYId,
                )
                val song5 = Song(
                    title = "EVERYTHING",
                    singer = "검정치마",
                    second = 0,
                    playTime = 200,
                    isPlaying = false,
                    music = R.raw.music_everything,
                    coverImg = R.drawable.img_album_exp8,
                    isLike = false,
                    albumIdx = AlbumTEAMBABYId,
                )

                songRef.child(uid).child("3").setValue(song3)
                songRef.child(uid).child("4").setValue(song4)
                songRef.child(uid).child("5").setValue(song5)

                songDao.insert(song3)
                songDao.insert(song4)
                songDao.insert(song5)
            }
        }
    }

    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        if (song.playTime > 0) {
            binding.sbMainProcess.progress = (song.second * 100000) / song.playTime
        } else {
            binding.sbMainProcess.progress = 0
        }
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songId = sharedPreferences.getInt("songId", 1)
        val songSecond = sharedPreferences.getInt("songSecond", 0)

        lifecycleScope.launch(Dispatchers.IO){
            val isSongDb = db.songDao().getSong(songId)

            if(isSongDb != null){
                song = isSongDb
                song.second = songSecond
            }
            else{
                song = Song(
                    title = "라일락",
                    singer = "아이유(IU)",
                    second = 0,
                    playTime = 200,
                    isPlaying = false,
                    music = R.raw.music_lilac,
                    coverImg = R.drawable.img_album_exp2,
                    isLike = false,
                    albumIdx = 1,
                    songId = 1
                )
            }

            launch(Dispatchers.Main){
                setMiniPlayer(song)
            }
        }
    }

    fun updateMiniPlayer(album: Album){
        binding.mainMiniplayerTitleTv.text = album.title
        binding.mainMiniplayerSingerTv.text = album.singer
    }
}