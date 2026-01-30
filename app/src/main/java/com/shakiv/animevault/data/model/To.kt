package com.shakiv.animevault.data.model

import com.google.gson.annotations.SerializedName


data class To (

  @SerializedName("day"   ) var day   : String? = null,
  @SerializedName("month" ) var month : String? = null,
  @SerializedName("year"  ) var year  : String? = null

)