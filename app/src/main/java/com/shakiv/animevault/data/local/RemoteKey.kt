package com.shakiv.animevault.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val animeId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)