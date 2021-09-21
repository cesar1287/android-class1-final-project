package com.github.cesar1287.class1dhfinalproject.features.home.repository

import android.app.Application
import com.github.cesar1287.class1dhfinalproject.api.ApiService
import com.github.cesar1287.class1dhfinalproject.base.BaseRepository
import com.github.cesar1287.class1dhfinalproject.database.Class1Database
import com.github.cesar1287.class1dhfinalproject.model.Genre
import com.github.cesar1287.class1dhfinalproject.model.Result
import com.github.cesar1287.class1dhfinalproject.model.toGenreDb
import com.github.cesar1287.class1dhfinalproject.model.toMovieDb
import com.github.cesar1287.class1dhfinalproject.modeldb.GenreDb
import com.github.cesar1287.class1dhfinalproject.modeldb.Movie
import com.github.cesar1287.class1dhfinalproject.utils.ResponseApi

class HomeRepository(
    val application: Application
) : BaseRepository() {

    suspend fun getNowPlayingMovies(page: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getNowPlayingMovies(page)
        }
    }

    suspend fun getPopularMovies(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getPopularMovies()
        }
    }

    suspend fun getMovieById(id: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieById(id)
        }
    }

    suspend fun getGenres(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getGenres()
        }
    }

    suspend fun saveGenresDatabase(genres: List<Genre>?) {
        genres?.let { genresApi ->
            val genresDb: MutableList<GenreDb> = mutableListOf()

            genresApi.forEach {
                genresDb.add(it.toGenreDb())
            }

            Class1Database
                .getDatabase(application)
                .genreDao()
                .insertAllGenres(
                    genresDb
                )
        }
    }

    suspend fun saveMoviesDb(movies: List<Result>) {
        val moviesDb: MutableList<Movie> = mutableListOf()

        movies.forEach {
            moviesDb.add(it.toMovieDb())
        }

        Class1Database
            .getDatabase(application)
            .movieDao()
            .insertAllMovies(
                moviesDb
            )
    }
}