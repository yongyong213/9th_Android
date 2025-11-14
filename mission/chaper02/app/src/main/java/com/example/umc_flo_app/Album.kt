package com.example.umc_flo_app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = true)
    var albumId: Int = 0,

    var title: String = "",
    var singer: String = "",
    var coverImg: Int = 0,
    var islike: Boolean = false,
)
