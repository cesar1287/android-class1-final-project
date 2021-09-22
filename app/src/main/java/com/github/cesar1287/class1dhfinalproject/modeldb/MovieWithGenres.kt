package com.github.cesar1287.class1dhfinalproject.modeldb

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.github.cesar1287.class1dhfinalproject.model.Result
import com.github.cesar1287.class1dhfinalproject.model.toMovieDb

data class MovieWithGenres(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreDb>
)

fun MovieWithGenres.toResultFromApi(): Result {
    return Result(
        adult = this.movie.adult,
        backdrop_path = this.movie.backdrop_path,
        genre_ids = this.genres.map { it.id },
        id = this.movie.id,
        original_language = this.movie.original_language,
        original_title = this.movie.original_title,
        overview = this.movie.overview,
        popularity = this.movie.popularity,
        poster_path = this.movie.poster_path,
        release_date = this.movie.release_date,
        title = this.movie.title,
        video = this.movie.video,
        vote_average = this.movie.vote_average,
        vote_count = this.movie.vote_count
    )
}