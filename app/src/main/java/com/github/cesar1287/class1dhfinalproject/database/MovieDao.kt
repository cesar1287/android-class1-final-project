package com.github.cesar1287.class1dhfinalproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.github.cesar1287.class1dhfinalproject.model.Result

@Dao
interface MovieDao {

    @Query("SELECT * FROM result")
    suspend fun getAllMovies(): List<Result>

    @Query("SELECT * FROM result WHERE id = :movieId")
    suspend fun loadMovieById(movieId: Int): List<Result>

    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(moviesList: List<Result>)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovie(movie: Result)

    @Delete
    suspend fun delete(movie: Result)
}