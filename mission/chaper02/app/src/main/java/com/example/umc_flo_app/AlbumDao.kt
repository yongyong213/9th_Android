package com.example.umc_flo_app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlbumDao {
    @Insert
    fun insert(album: Album): Long

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums(): List<Album>

    @Query("SELECT * FROM AlbumTable WHERE albumId = :albumId")
    fun getAlbum(albumId: Int): Album?

    @Update
    fun update(album: Album)

    @Query("SELECT * FROM AlbumTable WHERE isLike = 1")
    fun getLikedAlbums(): List<Album>
}