package com.github.cesar1287.class1dhfinalproject.features.moviedetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.cesar1287.class1dhfinalproject.base.BaseViewModel
import com.github.cesar1287.class1dhfinalproject.features.moviedetail.usecase.MovieDetailUseCase
import com.github.cesar1287.class1dhfinalproject.model.Movie
import kotlinx.coroutines.launch

class MovieDetailViewModel: BaseViewModel() {

    private val movieDetailUseCase = MovieDetailUseCase()

    private val _onSuccessMovieById: MutableLiveData<Movie> = MutableLiveData()
    val onSuccessMovieById: LiveData<Movie>
     get() = _onSuccessMovieById

    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            callApi(
                suspend { movieDetailUseCase.getMovieById(movieId) },
                onSuccess = {
                    _onSuccessMovieById.postValue(it as? Movie)
                }
            )
        }
    }
}