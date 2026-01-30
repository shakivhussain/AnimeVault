package com.shakiv.animevault.data.model

import com.google.gson.annotations.SerializedName


data class Aired (

  @SerializedName("from"   ) var from   : String? = null,
  @SerializedName("to"     ) var to     : String? = null,
  @SerializedName("prop"   ) var prop   : Prop?   = Prop(),
  @SerializedName("string" ) var string : String? = null

)