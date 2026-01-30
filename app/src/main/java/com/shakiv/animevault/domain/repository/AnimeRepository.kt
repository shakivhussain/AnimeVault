package com.shakiv.animevault.domain.repository

import androidx.paging.PagingData
import com.shakiv.animevault.data.common.Resource
import com.shakiv.animevault.data.local.AnimeEntity
import com.shakiv.animevault.data.model.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun getAnimeStream(): Flow<PagingData<AnimeEntity>>

    fun getAnimeDetails(id: Int): Flow<Resource<Anime?>>
}