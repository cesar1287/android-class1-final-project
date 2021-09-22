package com.github.cesar1287.class1dhfinalproject.features.home.usecase

import android.app.Application
import com.github.cesar1287.class1dhfinalproject.extensions.getFullImageUrl
import com.github.cesar1287.class1dhfinalproject.features.home.repository.HomeRepository
import com.github.cesar1287.class1dhfinalproject.model.GenreInfo
import com.github.cesar1287.class1dhfinalproject.model.NowPlaying
import com.github.cesar1287.class1dhfinalproject.model.Result
import com.github.cesar1287.class1dhfinalproject.modeldb.toResultFromApi
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Home.FIRST_PAGE
import com.github.cesar1287.class1dhfinalproject.utils.ResponseApi

class HomeUseCase(
    private val application: Application
) {

    private val homeRepository = HomeRepository(application)

    suspend fun getNowPlayingMovies(): ResponseApi {
        return when (val responseApi = homeRepository.getNowPlayingMovies(FIRST_PAGE)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? NowPlaying
                val result = data?.results?.map {
                    it.backdrop_path = it.backdrop_path?.getFullImageUrl()
                    it.poster_path = it.poster_path?.getFullImageUrl()
                    it
                }
                ResponseApi.Success(result)
            }
            is ResponseApi.Error -> {
                responseApi
            }
        }
    }

    suspend fun getPopularMovies() =
        homeRepository.getPopularMovies()

    suspend fun getMovieById(id: Int) =
        homeRepository.getMovieById(id)

    fun setupMoviesList(list: NowPlaying?): List<Result> {
        return list?.results?.map {
            it.backdrop_path = it.backdrop_path?.getFullImageUrl()
            it.poster_path = it.poster_path?.getFullImageUrl()
            it
        } ?: listOf()
    }

    suspend fun getGenres(): ResponseApi {
        return when(val response = homeRepository.getGenres()) {
            is ResponseApi.Success -> {
                val genreInfo = response.data as? GenreInfo
                homeRepository.saveGenresDatabase(genreInfo?.genres)
                response
            }
            is ResponseApi.Error -> {
                response
            }
        }
    }

    suspend fun saveMoviesDb(movies: List<Result>) {
        homeRepository.saveMoviesDb(movies)
    }

    suspend fun getMoviesFromDb(): List<Result> {
        val movieGenreList = homeRepository.getNowPlayingMoviesFromDb()
        return movieGenreList.map {
            it.toResultFromApi()
        }
    }
}