package com.shakiv.animevault.presentation.common

sealed class Screen(val route: String) {
    companion object {
        const val ARG_ANIME_ID = "anime_id"
    }

    data object AnimeList : Screen("anime_list_screen")

    data object AnimeDetail : Screen("anime_detail_screen/{$ARG_ANIME_ID}") {

        fun createRoute(animeId: Int): String {
            return "anime_detail_screen/$animeId"
        }
    }
}