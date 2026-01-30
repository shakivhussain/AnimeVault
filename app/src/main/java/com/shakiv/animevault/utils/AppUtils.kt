package com.shakiv.animevault.utils

import android.net.Uri

object AppUtils {
    const val BASE_URL ="https://api.jikan.moe/v4/"
    const val APP_NAME ="Anime Vault"


    fun extractYoutubeVideoId(url: String?): String? {
        if (url.isNullOrBlank()) return null

        return try {
            val uri = Uri.parse(url)

            // Case 1: /embed/{videoId}
            uri.pathSegments
                ?.firstOrNull { it.isNotBlank() && it != "embed" }
                ?.takeIf { it.length >= 8 }
        } catch (e: Exception) {
            null
        }
    }

}