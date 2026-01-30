package com.shakiv.animevault.di

import android.content.Context
import com.shakiv.animevault.data.local.AnimeDatabase
import com.shakiv.animevault.data.remote.JikanApi
import com.shakiv.animevault.data.repository.AnimeRepositoryImp
import com.shakiv.animevault.domain.repository.AnimeRepository
import com.shakiv.animevault.utils.AppUtils.BASE_URL
import com.shakiv.animevault.utils.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideJikanApi(retrofit: Retrofit) = retrofit.create(JikanApi::class.java)


    @Provides
    fun provideAnimeRepository(jikanApi: JikanApi, animedb : AnimeDatabase) : AnimeRepository{
        return AnimeRepositoryImp(jikanApi, animedb)
    }



    @Provides
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor {
        return NetworkMonitor(context)
    }

}