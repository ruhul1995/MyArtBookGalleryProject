package com.example.myartbookgalleryproject.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Art)

    @Delete
    suspend fun deleteArt(art: Art)

    //will give us live data
    @Query("SELECT * FROM arts")
    fun observeArt(): LiveData<List<Art>>

}