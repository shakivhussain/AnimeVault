package com.shakiv.animevault.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime_table ORDER BY page ASC, score DESC")
    fun getAllAnime(): PagingSource<Int,AnimeEntity>

    @Query("SELECT * FROM anime_table WHERE malId = :id")
    suspend fun getAnimeById(id: Int): AnimeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<AnimeEntity>)

    @Query("DELETE FROM anime_table")
    suspend fun clearAll()

}