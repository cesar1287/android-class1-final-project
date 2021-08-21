package com.github.cesar1287.class1dhfinalproject.features.home.usecase

import com.github.cesar1287.class1dhfinalproject.extensions.getFullImageUrl
import com.github.cesar1287.class1dhfinalproject.features.home.repository.HomeRepository
import com.github.cesar1287.class1dhfinalproject.model.NowPlaying
import com.github.cesar1287.class1dhfinalproject.utils.ResponseApi

class HomeUseCase {

    private val homeRepository = HomeRepository()

    suspend fun getNowPlayingMovies(): ResponseApi {
        return when (val responseApi = homeRepository.getNowPlayingMovies()) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? NowPlaying
                val result = data?.results?.map {
                    it.backdrop_path = it.backdrop_path.getFullImageUrl()
                    it.poster_path = it.poster_path.getFullImageUrl()
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
}