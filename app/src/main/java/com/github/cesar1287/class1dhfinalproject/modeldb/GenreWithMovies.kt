package com.github.cesar1287.class1dhfinalproject.modeldb

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.github.cesar1287.class1dhfinalproject.model.Result

data class GenreWithMovies(
    @Embedded val genreDb: GenreDb,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val movies: List<MovieDb>
)