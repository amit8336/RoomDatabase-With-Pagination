package com.example.roomdatabase.pagination.Roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdatabase.pagination.Api.ApiDao
import com.example.roomdatabase.pagination.Api.ApiModel
import com.example.roomdatabase.pagination.FavouriteDatabase.FavouriteDao
import com.example.roomdatabase.pagination.FavouriteDatabase.favouritemodel

@Database(entities = [ApiModel::class , favouritemodel::class], version = 5)
abstract class RoomDatabase : RoomDatabase() {
    companion object {
        var db: com.example.roomdatabase.pagination.Roomdatabase.RoomDatabase? = null
    }
    init {
        db = this
    }

    abstract fun movieDao(): ApiDao
    abstract fun favouriteDao(): FavouriteDao


}