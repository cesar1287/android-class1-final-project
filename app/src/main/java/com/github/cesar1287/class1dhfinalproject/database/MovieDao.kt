package com.github.cesar1287.class1dhfinalproject.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.github.cesar1287.class1dhfinalproject.modeldb.Movie
import com.github.cesar1287.class1dhfinalproject.modeldb.MovieWithGenres

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<MovieWithGenres>

    @Query("SELECT * FROM movie WHERE movieId = :movieId")
    suspend fun loadMovieById(movieId: Int): List<Movie>

    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(moviesList: List<Movie>)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)
}