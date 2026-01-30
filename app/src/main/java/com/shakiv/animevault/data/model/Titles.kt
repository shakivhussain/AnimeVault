package com.shakiv.animevault.data.model

import com.google.gson.annotations.SerializedName


data class Titles (

  @SerializedName("type"  ) var type  : String? = null,
  @SerializedName("title" ) var title : String? = null

)