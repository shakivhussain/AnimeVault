package com.shakiv.animevault.data.model

import com.google.gson.annotations.SerializedName


data class Pagination (

  @SerializedName("last_visible_page" ) var lastVisiblePage : Int?     = null,
  @SerializedName("has_next_page"     ) var hasNextPage     : Boolean? = null,
  @SerializedName("current_page"      ) var currentPage     : Int?     = null,
  @SerializedName("items"             ) var items           : Items?   = Items()

)