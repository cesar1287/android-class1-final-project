package com.github.cesar1287.class1dhfinalproject.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.cesar1287.class1dhfinalproject.base.BaseViewModel
import com.github.cesar1287.class1dhfinalproject.features.home.usecase.HomeUseCase
import com.github.cesar1287.class1dhfinalproject.model.Popular
import com.github.cesar1287.class1dhfinalproject.model.Result
import com.github.cesar1287.class1dhfinalproject.utils.ResponseApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel : BaseViewModel() {

    private val homeUseCase = HomeUseCase()

    private val _onSuccessNowPlaying: MutableLiveData<List<Result>> =
        MutableLiveData()
    val onSuccessNowPlaying: LiveData<List<Result>>
        get() = _onSuccessNowPlaying

    private val _onErrorNowPlaying: MutableLiveData<Int> =
        MutableLiveData()
    val onErrorNowPlaying: LiveData<Int>
        get() = _onErrorNowPlaying

    private val _onCustomErrorNowPlaying: MutableLiveData<Boolean> =
        MutableLiveData()
    val onCustomErrorNowPlaying: LiveData<Boolean>
        get() = _onCustomErrorNowPlaying

    private val _onSuccessPopular: MutableLiveData<Popular> =
        MutableLiveData()
    val onSuccessPopular: LiveData<Popular>
        get() = _onSuccessPopular

    private val _onErrorPopular: MutableLiveData<Int> =
        MutableLiveData()
    val onErrorPopular: LiveData<Int>
        get() = _onErrorPopular

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getNowPlayingMovies() },
                onSuccess = {
                    val result = it as? List<*>
                    _onSuccessNowPlaying.postValue(
                        result?.filterIsInstance<Result>()
                    )
                },
                onError = {
                    _onCustomErrorNowPlaying.postValue(true)
                }
            )
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getPopularMovies() },
                onSuccess = {
                    _onSuccessPopular.postValue(
                        it as? Popular
                    )
                }
            )
        }
    }
}