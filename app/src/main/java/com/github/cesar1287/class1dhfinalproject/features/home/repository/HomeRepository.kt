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
import com.github.cesar1287.class1dhfinalproject.modeldb.MovieDb
import com.github.cesar1287.class1dhfinalproject.modeldb.MovieGenreCrossRef
import com.github.cesar1287.class1dhfinalproject.modeldb.MovieWithGenres
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
        val moviesDb: MutableList<MovieDb> = mutableListOf()

        movies.forEach { movie ->
            movie.genre_ids?.forEach { genreId ->
                val movieGenreCrossRef = MovieGenreCrossRef(
                    genreId = genreId,
                    movieId = movie.id ?: -1
                )
                Class1Database
                    .getDatabase(application)
                    .movieGenreDao()
                    .insertMovieGenre(movieGenreCrossRef)
            }
            moviesDb.add(movie.toMovieDb())
        }

        Class1Database
            .getDatabase(application)
            .movieDao()
            .insertAllMovies(
                moviesDb
            )
    }

    suspend fun getNowPlayingMoviesFromDb(): List<MovieWithGenres> {
        val movieDao = Class1Database.getDatabase(application).movieDao()
        return movieDao.getAllMovies()
    }
}