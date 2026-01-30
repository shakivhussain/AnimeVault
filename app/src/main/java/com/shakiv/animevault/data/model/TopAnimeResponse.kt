package com.shakiv.animevault.data.model

import com.google.gson.annotations.SerializedName


data class TopAnimeResponse (

  @SerializedName("pagination" ) var pagination : Pagination?     = Pagination(),
  @SerializedName("data"       ) var data       : ArrayList<Anime> = arrayListOf()

)

data class AnimeResponse(
    val data: List<AnimeDto>,
    val pagination: PaginationDto
)

data class AnimeDto(
    @SerializedName("mal_id") val malId: Int,
    val title: String,
    val images: ImageDto,
    val score: Double,
    val episodes: Int?,
    val synopsis: String?
)

data class ImageDto(val jpg: ImageUrlDto)
data class ImageUrlDto(@SerializedName("image_url") val imageUrl: String)
data class PaginationDto(@SerializedName("has_next_page") val hasNextPage: Boolean)