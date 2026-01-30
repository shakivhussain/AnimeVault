package com.shakiv.animevault.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shakiv.animevault.data.local.typeconverter.GenreConverters

@Database(entities = [AnimeEntity::class, RemoteKey::class], version = 1, exportSchema = false)
@TypeConverters(GenreConverters::class)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}
