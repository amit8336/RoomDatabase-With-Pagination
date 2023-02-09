package com.example.roomdatabase.pagination.FavouriteDatabase

import androidx.room.*


@Dao
interface FavouriteDao {

    @Query("Select * from favouriteTable")
    fun getAllMovie(): List<favouritemodel>

    @Query("SELECT EXISTS (SELECT 1 FROM favouriteTable WHERE favourite=:id)")
    fun getAllFavourite(id: Int): Int

    @Insert
    fun addData(fav: favouritemodel)

    @Update
    fun update(fav: favouritemodel)

//    @Delete
//    fun deleteMovie(fav: favouritemodel)

    @Query("DELETE FROM favouriteTable WHERE favourite = :id")
    fun deleteMovie(id: Int): Int


}