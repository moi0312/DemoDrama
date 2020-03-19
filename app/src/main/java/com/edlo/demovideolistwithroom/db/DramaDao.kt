package com.edlo.demovideolistwithroom.db

import androidx.room.*

@Dao
interface DramaDao {
    @Query("SELECT *, rowid FROM table_drama")
    fun getAll(): List<Drama>

    @Query("SELECT *, rowid FROM table_drama WHERE rowid IN (:dramaIds)")
    fun loadAllByIds(dramaIds: IntArray): List<Drama>

    @Query("SELECT *, rowid FROM table_drama WHERE name LIKE (:dramaName)")
    fun loadAllByName(dramaName: String): List<Drama>

//    @Query("SELECT * FROM drama WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): Drama

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Drama)

    @Update
    fun update(user: Drama)

    @Delete
    fun delete(user: Drama)
}