package com.example.myartbookgalleryproject.di

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myartbookgalleryproject.R
import com.example.myartbookgalleryproject.api.RetrofitAPI
import com.example.myartbookgalleryproject.repository.ArtRepository
import com.example.myartbookgalleryproject.repository.ArtRepositoryInterface
import com.example.myartbookgalleryproject.roomdb.ArtDao
import com.example.myartbookgalleryproject.roomdb.ArtDatabase
import com.example.myartbookgalleryproject.util.Util.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Benefit of DI -
// 1) we create retrofit, database only once
//2) it will be injected whenever needed.


@Module
@InstallIn(SingletonComponent::class) // ApplicationComponent is deprecated. // latest is SingletonComponent
object AppModule {

    @Provides
    fun providesArtDatabase(application: Application): ArtDatabase {
        return ArtDatabase.getInstance(application)
    }

    //Instance of database
    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ArtDatabase::class.java, "ArtDataBook"
    )

    //Doing for Dao || instance of Dao
    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    //instance of Retrofit
    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo( dao: ArtDao, api: RetrofitAPI) = ArtRepository(dao,api) as ArtRepositoryInterface


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}
