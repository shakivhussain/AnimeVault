package com.shakiv.animevault.data.paging

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shakiv.animevault.data.local.AnimeDatabase
import com.shakiv.animevault.data.local.AnimeEntity
import com.shakiv.animevault.data.local.RemoteKey
import com.shakiv.animevault.data.mapper.toEntity
import com.shakiv.animevault.data.remote.JikanApi
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val api: JikanApi,
    private val db: AnimeDatabase
) : RemoteMediator<Int, AnimeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }

        return try {
            val response = api.getTopAnime(page = page, limit = state.config.pageSize)
            val endOfPaginationReached = !(response.pagination?.hasNextPage?:true)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeyDao().clearRemoteKeys()
                    db.animeDao().clearAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = response.data.map {
                    RemoteKey(animeId = it.malId?:-1, prevKey = prevKey, nextKey = nextKey)
                }

                db.remoteKeyDao().insertAll(keys)
                db.animeDao().insertAll(response.data.map { dto ->
                    dto.toEntity()
                })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: retrofit2.HttpException) {
            MediatorResult.Error(e)
        } catch (e: java.io.IOException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AnimeEntity>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { anime ->
            db.remoteKeyDao().getRemoteKeyByAnimeId(anime.malId?:1)
        }
    }

    private suspend fun getRemoteKeyClosestToPosition(state: PagingState<Int, AnimeEntity>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.malId?.let { id ->
                db.remoteKeyDao().getRemoteKeyByAnimeId(id)
            }
        }
    }
}