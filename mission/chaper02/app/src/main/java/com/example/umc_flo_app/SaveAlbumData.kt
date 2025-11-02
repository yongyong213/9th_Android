package com.example.umc_flo_app

data class SaveAlbumData(
    val title: String,
    val singer: String,
    val info: String,
    val coverImgRes: Int,
    var isPlaying: Boolean = false
)
