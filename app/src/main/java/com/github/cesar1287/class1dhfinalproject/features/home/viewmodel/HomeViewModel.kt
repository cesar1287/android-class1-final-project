package com.github.cesar1287.class1dhfinalproject.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.cesar1287.class1dhfinalproject.features.home.usecase.HomeUseCase
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val homeUseCase = HomeUseCase()

    //homeFragment - homeViewModel

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            homeUseCase.getNowPlayingMovies()
        }
    }
}