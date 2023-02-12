package com.roomDb

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.myartbookgalleryproject.roomdb.Art
import com.example.myartbookgalleryproject.roomdb.ArtDao
import com.example.myartbookgalleryproject.roomdb.ArtDatabase
import com.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//We are writing roomdb test in AndroidTest build folder because injectRoomDatabase in AppModules requires context.

@SmallTest
@ExperimentalCoroutinesApi
class ArtDaoTest {

    @get:Rule
    var instantTestExecutor = InstantTaskExecutorRule()

    private lateinit var database: ArtDatabase
    private lateinit var dao: ArtDao

    @Before
    fun setup()
    {
        //temporary database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ArtDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.artDao()
    }

    //This will be executed after every test
    @After
    fun tearDown()
    {
        database.close()
    }

    //Testing room database functionality now

    //we will run coroutine synchronously
    @Test
    fun insertArtTesting() = runBlockingTest {

        //fake art
        val exampleArt = Art("Mona lisa", "Da Vinci", 1300, "test.com", 1)
        dao.insertArt(exampleArt)

        val list = dao.observeArt().getOrAwaitValue() // observeArt is livedata, so we need a normal data, converting using livedataUtil Class using the method getorAwaitValue
        assertThat(list).contains(exampleArt)
    }


    @Test
    fun deleteArtTesting() = runBlockingTest {
        val exampleArt = Art("Mona lisa", "Da Vinci", 1300, "test.com", 1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)
        val list = dao.observeArt().getOrAwaitValue() // observeArt is livedata, so we need a normal data, converting using livedataUtil Class using the method getorAwaitValue
        assertThat(list).doesNotContain(exampleArt)
    }
}