package com.shakiv.animevault.data.model

import com.google.gson.annotations.SerializedName

data class AnimeDetailsResponse (

  @SerializedName("data" ) var anime : Anime = Anime()

)