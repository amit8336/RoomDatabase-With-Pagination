package com.example.roomdatabase.pagination.Api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movieTable")
data class ApiModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("albumId")
    @ColumnInfo(name = "albumId")
    var albumid: Int,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String,

    @SerializedName("url")
    @ColumnInfo(name = "url")
    var url: String,

    @SerializedName("thumbnailUrl")
    @ColumnInfo(name = "thumbnailUrl")
    val thumbnailurl: String,

    @ColumnInfo("favourite")
    var favourite: Int
) {
    constructor() : this(0, 0, "", "", "", 0)


}