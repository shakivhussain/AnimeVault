package com.shakiv.animevault.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey var malId          : Int?                    = null,
    val title: String,
    val episodes: Int?,
    val imageUrl: String?,
    val score: Double?,
    val synopsis: String?,
    val genres: List<String>,
    val trailerUrl: String?,
    val page: Int,
    val year: Int? = null,
    val type : String? = null
)
