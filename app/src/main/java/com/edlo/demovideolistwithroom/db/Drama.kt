package com.edlo.demovideolistwithroom.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Fts4 //support full-text search(FTS)
@Entity(tableName = "table_drama")
data class Drama (
    @SerializedName("drama_id")
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val id: Int,

    @ColumnInfo
    val name: String?,

    @SerializedName("total_views")
    @ColumnInfo(name = "total_views")
    val totalViews: String?,

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String?,

    @ColumnInfo val thumb: String?,
    @ColumnInfo val rating: Float?
): Serializable