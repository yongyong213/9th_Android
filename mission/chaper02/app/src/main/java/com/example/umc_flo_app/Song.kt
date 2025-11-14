package com.example.umc_flo_app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(
    @PrimaryKey(autoGenerate = true)
    var songId: Int = 0,

    val title : String = "",
    val singer : String = "",
    var second : Int = 0,
    var playTime : Int = 60,
    var isPlaying: Boolean = false,
    var music: Int = 0,
    var coverImg: Int = 0,
    var isLike: Boolean = false,

    var albumIdx: Int = 0
)
