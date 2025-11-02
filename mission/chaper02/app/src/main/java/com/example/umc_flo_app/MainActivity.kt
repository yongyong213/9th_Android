package com.example.umc_flo_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.umc_flo_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        binding.mainBnv.setupWithNavController(navController)

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), 0, 60, false, "music_lilac")

        binding.mainMiniplayerCl.setOnClickListener{
            val intent =Intent(this, SongActivity::class.java)

            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
            startActivity(intent)
        }
    }

    fun updateMiniPlayer(album: Album){
        binding.mainMiniplayerTitleTv.text = album.title
        binding.mainMiniplayerSingerTv.text = album.singer
    }
}