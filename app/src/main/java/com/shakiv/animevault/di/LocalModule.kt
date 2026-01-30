package com.shakiv.animevault.di

import android.app.Application
import androidx.room.Room
import com.shakiv.animevault.data.local.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    fun provideAnimeDatabase(application: Application): AnimeDatabase {
        return Room.databaseBuilder(
            application,
            AnimeDatabase::class.java,
            "anime_database")
            .fallbackToDestructiveMigration()
            .build()
    }

}