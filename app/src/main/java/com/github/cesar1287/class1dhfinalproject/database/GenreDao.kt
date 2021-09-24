package com.github.cesar1287.class1dhfinalproject.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.github.cesar1287.class1dhfinalproject.modeldb.GenreDb
import com.github.cesar1287.class1dhfinalproject.modeldb.GenreWithMovies
import com.github.cesar1287.class1dhfinalproject.modeldb.MovieWithGenres

@Dao
interface GenreDao {

    @Transaction
    @Query("SELECT * FROM genre")
    suspend fun getAllMoviesFromGenre(): List<GenreWithMovies>

    @Query("SELECT * FROM genre")
    suspend fun getAllGenres(): List<GenreDb>

    @Query("SELECT * FROM genre WHERE genreId = :genreId")
    suspend fun loadGenreById(genreId: Int): List<GenreDb>

    @Insert(onConflict = REPLACE)
    suspend fun insertAllGenres(moviesList: List<GenreDb>)

    @Insert(onConflict = REPLACE)
    suspend fun insertGenre(genre: GenreDb)

    @Delete
    suspend fun delete(genreDb: GenreDb)
}