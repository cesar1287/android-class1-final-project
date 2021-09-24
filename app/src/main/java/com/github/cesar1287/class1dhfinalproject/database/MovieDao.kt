package com.github.cesar1287.class1dhfinalproject.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.github.cesar1287.class1dhfinalproject.modeldb.MovieDb
import com.github.cesar1287.class1dhfinalproject.modeldb.MovieWithGenres

@Dao
interface MovieDao {

    //CRUD - CREATE - READ - UPDATE - DELETE

    //READ
    @Transaction
    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<MovieWithGenres>

    //READ
    @Query("SELECT * FROM movie WHERE movieId = :movieId")
    suspend fun loadMovieById(movieId: Int): MovieDb

    //CREATE
    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(moviesList: List<MovieDb>)

    //CREATE
    @Insert(onConflict = REPLACE)
    suspend fun insertMovie(movieDb: MovieDb)

    //UPDATE
    @Update(onConflict = REPLACE)
    suspend fun updateMovie(movieDb: MovieDb)

    //DELETE
    @Delete
    suspend fun delete(movieDb: MovieDb)
}