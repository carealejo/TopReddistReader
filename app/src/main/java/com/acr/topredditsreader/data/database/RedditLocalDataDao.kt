package com.acr.topredditsreader.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RedditLocalDataDao {
    @Query("SELECT * FROM redditlocaldata")
    fun getAll(): List<RedditLocalData>

    @Insert
    fun insert(redditLocalData: RedditLocalData): Long

    @Delete
    fun delete(redditLocalData: RedditLocalData)
}