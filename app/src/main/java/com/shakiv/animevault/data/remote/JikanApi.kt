package com.shakiv.animevault.data.remote

import com.shakiv.animevault.data.model.AnimeDetailsResponse
import com.shakiv.animevault.data.model.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApi {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ) : TopAnimeResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") id: Int
    ) : AnimeDetailsResponse
}
