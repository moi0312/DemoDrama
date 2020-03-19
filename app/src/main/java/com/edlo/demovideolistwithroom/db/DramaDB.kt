package com.edlo.demovideolistwithroom.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Drama::class), version = 1)
abstract class DramaDB : RoomDatabase() {
    companion object {
        val DB_NAME = "db_drama"
    }
    abstract fun dramaDao(): DramaDao
}