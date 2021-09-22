package com.github.cesar1287.class1dhfinalproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.github.cesar1287.class1dhfinalproject.modeldb.GenreDb
import com.github.cesar1287.class1dhfinalproject.modeldb.MovieGenreCrossRef

@Dao
interface MovieGenreCrossRefDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovieGenre(moviesGenresList: List<MovieGenreCrossRef>)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovieGenre(movieGenre: MovieGenreCrossRef)

    @Delete
    suspend fun delete(movieGenre: MovieGenreCrossRef)
}