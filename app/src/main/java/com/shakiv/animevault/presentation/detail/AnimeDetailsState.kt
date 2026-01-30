package com.shakiv.animevault.presentation.detail

import com.shakiv.animevault.data.local.AnimeEntity
import com.shakiv.animevault.data.model.Anime

data class AnimeDetailState(
    val isLoading: Boolean = false,
    val anime: AnimeEntity? = null,
    val error: String? = null
)