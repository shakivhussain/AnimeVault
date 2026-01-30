package com.shakiv.animevault.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakiv.animevault.data.common.Resource
import com.shakiv.animevault.data.local.AnimeDao
import com.shakiv.animevault.data.local.AnimeDatabase
import com.shakiv.animevault.data.local.AnimeEntity
import com.shakiv.animevault.data.mapper.toDomain
import com.shakiv.animevault.data.mapper.toEntity
import com.shakiv.animevault.data.model.Anime
import com.shakiv.animevault.data.paging.AnimeRemoteMediator
import com.shakiv.animevault.data.remote.JikanApi
import com.shakiv.animevault.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AnimeRepositoryImp @Inject constructor(
    private val jikanApi: JikanApi,
    private val db: AnimeDatabase
) : AnimeRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAnimeStream(): Flow<PagingData<AnimeEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20,
                prefetchDistance = 2,
                initialLoadSize = 20
                ),
            remoteMediator = AnimeRemoteMediator(jikanApi, db),
            pagingSourceFactory = { db.animeDao().getAllAnime() }
        ).flow
    }


    override fun getAnimeDetails(id: Int): Flow<Resource<Anime?>> = flow{
        emit(Resource.Loading(true))

        val localAnime = db.animeDao().getAnimeById(id)?.toDomain()

        if (localAnime != null){
            emit(Resource.Success(localAnime))
        }

        try {
            val remoteData = jikanApi.getAnimeDetails(id)
            val entity = remoteData.anime.toEntity()
            db.animeDao().insertAll(listOf(entity))
            emit(Resource.Success(entity.toDomain()))
        }catch (e: IOException){
            if (localAnime==null){
                emit(Resource.Error("Failed to load details."))
            }
        }
    }

}