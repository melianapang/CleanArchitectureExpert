package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.ShowEntity

@Database(
    entities = [MovieEntity::class, ShowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}