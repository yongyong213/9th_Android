package com.example.umc_flo_app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {
    @Insert
    fun insert(album: Album): Long

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums(): List<Album>
}