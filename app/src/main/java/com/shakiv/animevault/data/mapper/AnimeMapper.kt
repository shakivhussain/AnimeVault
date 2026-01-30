package com.shakiv.animevault.data.mapper

import com.shakiv.animevault.data.local.AnimeEntity
import com.shakiv.animevault.data.model.Anime
import com.shakiv.animevault.data.model.Genres
import com.shakiv.animevault.data.model.Images
import com.shakiv.animevault.data.model.Jpg
import com.shakiv.animevault.data.model.Trailer


fun Anime.toEntity(): AnimeEntity {
    return AnimeEntity(
        malId = this.malId ?: 0,
        title = this.title ?: "Unknown Title",
        episodes = this.episodes,
        imageUrl = this.images?.jpg?.imageUrl.orEmpty(),
        score = this.score,
        synopsis = this.synopsis,
        genres = this.genres.map { it.name ?: "" },
        trailerUrl = this.trailer?.embedUrl ?: this.trailer?.url,
        page = 1,
        year = this.year,
        type = this.type
    )
}

fun AnimeEntity.toDomain(): Anime {
    return Anime(
        malId = this.malId,
        title = this.title,
        episodes = this.episodes,
        images = Images(jpg = Jpg(imageUrl = this.imageUrl)),
        score = this.score,
        synopsis = this.synopsis,
        trailer = Trailer(embedUrl = this.trailerUrl, url = this.trailerUrl),
        genres = if (this.genres.isEmpty()) {
            arrayListOf()
        } else {
            ArrayList(this.genres.map {
                Genres(name = it.trim())
            })
        },

        approved = null,
        titles = arrayListOf(),
        producers = arrayListOf(),
        year = this.year,
        status = this.type

    )
}