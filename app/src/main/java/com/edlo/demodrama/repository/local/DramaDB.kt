package com.edlo.demodrama.repository.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edlo.demodrama.MyDemoApplication

@Database(entities = arrayOf(Drama::class), exportSchema = false, version = 1)
abstract class DramaDB : RoomDatabase() {
    companion object {
        val DB_NAME = "db_drama"
        private var INSTANCE: DramaDB? = null

//        private val MIGRATION_1_2 = object: Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // Create a new table, copy data, remove the old table, Change the table name
//                database.execSQL(
//                    "CREATE TABLE IF NOT EXISTS drama_migrate (`rowid` INTEGER PRIMARY KEY NOT NULL, `name` TEXT NOT NULL, `total_views` TEXT NOT NULL, `created_at` TEXT NOT NULL, `thumb` TEXT NOT NULL, `rating` REAL NOT NULL DEFAULT 0.0)")
//                database.execSQL(
//                    "INSERT OR REPLACE INTO drama_migrate (rowid, name, total_views, created_at, thumb, rating) SELECT rowid, name, total_views, created_at, thumb, rating FROM table_drama")
//                database.execSQL("DROP TABLE table_drama")
//                database.execSQL("ALTER TABLE drama_migrate RENAME TO table_drama")
//            }
//        }

        fun getDatabase(): DramaDB {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder( MyDemoApplication.INSTANCE.applicationContext,
                        DramaDB::class.java, DB_NAME )
//                .addMigrations(MIGRATION_1_2)
                .build()
            }
            return INSTANCE!!
        }
    }
    abstract fun dramaDao(): DramaDao
}