package com.example.roomdatabase.pagination.FavouriteDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "favouriteTable")
data class favouritemodel(
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

    @SerializedName("favourite")
    @ColumnInfo("favourite")
    var favourite: Int ,
) {
    constructor() : this(0, 0, "", "", 0)
}
