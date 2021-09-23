package com.github.cesar1287.class1dhfinalproject.model

import com.github.cesar1287.class1dhfinalproject.modeldb.GenreDb

data class Genre(
    val id: Int,
    val name: String
)

fun Genre.toGenreDb(): GenreDb {
    return GenreDb(
        id = this.id,
        name = this.name,
        xpto = "xpto"
    )
}