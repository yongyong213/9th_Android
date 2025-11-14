package com.example.umc_flo_app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Album::class, Song::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun albumDao(): AlbumDao
    abstract fun songDao(): SongDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null;

        fun getInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "flo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
