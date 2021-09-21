package com.github.cesar1287.class1dhfinalproject.modeldb

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithGenres(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId"
    )
    val genres: List<GenreDb>
)
