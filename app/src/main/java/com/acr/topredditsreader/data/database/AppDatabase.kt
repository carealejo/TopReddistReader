package com.acr.topredditsreader.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RedditLocalData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun redditLocalDataDao(): RedditLocalDataDao
}