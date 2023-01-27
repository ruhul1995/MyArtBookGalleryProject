package com.example.myartbookgalleryproject.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides

@Database( entities = [Art::class], version = 1)
abstract class ArtDatabase: RoomDatabase() {
    abstract fun artDao(): ArtDao

    companion object {
        @Volatile
        private var instance: ArtDatabase? = null

        fun getInstance(context: Context): ArtDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, ArtDatabase::class.java, "art_db").build()
            }
        }
    }
}
