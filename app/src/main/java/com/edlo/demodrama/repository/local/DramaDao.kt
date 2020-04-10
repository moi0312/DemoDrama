package com.edlo.demodrama.repository.local

import androidx.room.*

@Dao
interface DramaDao {
    @Query("SELECT *, rowid FROM table_drama")
    suspend fun getAll(): List<Drama>

    @Query("SELECT *, rowid FROM table_drama WHERE name LIKE (:dramaName)")
    suspend fun findByName(dramaName: String): List<Drama>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Drama>)

    @Update
    suspend fun update(user: Drama)

    @Delete
    fun delete(user: Drama)
}