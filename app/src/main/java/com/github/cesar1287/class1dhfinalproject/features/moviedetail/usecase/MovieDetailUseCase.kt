package com.github.cesar1287.class1dhfinalproject.features.moviedetail.usecase

import com.github.cesar1287.class1dhfinalproject.extensions.getFullImageUrl
import com.github.cesar1287.class1dhfinalproject.features.moviedetail.repository.MovieDetailRepository
import com.github.cesar1287.class1dhfinalproject.model.Movie
import com.github.cesar1287.class1dhfinalproject.utils.ResponseApi

class MovieDetailUseCase {

    private val movieDetailRepository = MovieDetailRepository()

    suspend fun getMovieById(movieId: Int): ResponseApi {
        return when(val responseApi = movieDetailRepository.getMovieById(movieId)) {
            is ResponseApi.Success -> {
                val movie = responseApi.data as? Movie
                movie?.backdrop_path = movie?.backdrop_path?.getFullImageUrl()
                return ResponseApi.Success(movie)
            }
            is ResponseApi.Error -> {
                responseApi
            }
        }
    }
}