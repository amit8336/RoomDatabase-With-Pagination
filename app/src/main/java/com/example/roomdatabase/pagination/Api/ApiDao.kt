package com.example.roomdatabase.pagination.Api

import androidx.room.*

@Dao
interface ApiDao {

    @Query("Select * from movieTable")
    fun getAllMovie(): List<ApiModel>

    @Query("SELECT * FROM movieTable WHERE favourite= :id")
    fun getAllFavourite(id: Int): Int

    @Insert
    fun addMovie(apiModel: List<ApiModel>?)

    @Update
    fun update(apiModel: List<ApiModel>?)

    @Query("delete from movieTable")
    fun clearAllData()

    @Delete
    fun deleteMovie(apiModel: ApiModel)


}