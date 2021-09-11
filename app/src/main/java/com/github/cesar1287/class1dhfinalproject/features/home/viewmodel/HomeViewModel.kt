package com.github.cesar1287.class1dhfinalproject.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.github.cesar1287.class1dhfinalproject.base.BaseViewModel
import com.github.cesar1287.class1dhfinalproject.features.home.paging.HomeDataSourceFactory
import com.github.cesar1287.class1dhfinalproject.features.home.paging.HomePageKeyedDataSource
import com.github.cesar1287.class1dhfinalproject.features.home.repository.HomeRepository
import com.github.cesar1287.class1dhfinalproject.features.home.usecase.HomeUseCase
import com.github.cesar1287.class1dhfinalproject.model.Result
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Home.PAGE_SIZE
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    var moviesPagedList: LiveData<PagedList<Result>>? = null
    private var watchMoviesLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null

    private val homeUseCase = HomeUseCase()
    private val homeRepository = HomeRepository()

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        val homePageKeyedDataSource = HomePageKeyedDataSource(
            homeUseCase = homeUseCase,
            homeRepository = homeRepository
        )
        val homeDataSourceFactory = HomeDataSourceFactory(homePageKeyedDataSource)

        watchMoviesLiveDataSource = homeDataSourceFactory.getLiveDataSource()
        moviesPagedList = LivePagedListBuilder(homeDataSourceFactory, pagedListConfig)
            .build()

    }

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getMovieById(id) },
                onSuccess = {
                    it
                }
            )
        }
    }

//x    fun getNowPlayingMovies() {
//        viewModelScope.launch {
//            callApi(
//                suspend { homeUseCase.getNowPlayingMovies() },
//                onSuccess = {
//                    val result = it as? List<*>
//                    _onSuccessNowPlaying.postValue(
//                        result?.filterIsInstance<Result>()
//                    )
//                },
//                onError = {
//                    _onCustomErrorNowPlaying.postValue(true)
//                }
//            )
//        }
//    }
//
//    fun getPopularMovies() {
//        viewModelScope.launch {
//            callApi(
//                suspend { homeUseCase.getPopularMovies() },
//                onSuccess = {
//                    _onSuccessPopular.postValue(
//                        it as? Popular
//                    )
//                }
//            )
//        }
//    }
}