package com.github.cesar1287.class1dhfinalproject.features.home.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

class HomeViewModel(
    application: Application,
) : BaseViewModel(application) {

    var moviesPagedList: LiveData<PagedList<Result>>? = null
    private var watchMoviesLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null

    private val homeUseCase = HomeUseCase(getApplication())
    private val homeRepository = HomeRepository(getApplication<Application>())

    private val _onGenresLoaded: MutableLiveData<Boolean> =  MutableLiveData()
    val onGenresLoaded: LiveData<Boolean>
        get() = _onGenresLoaded

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

    fun getGenres() {
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getGenres() },
                onSuccess = {
                    _onGenresLoaded.postValue(true)
                }
            )
        }
    }

//    fun getMovieById(id: Int) {
//        viewModelScope.launch {
//            callApi(
//                suspend { homeUseCase.getMovieById(id) },
//                onSuccess = {
//                    it
//                }
//            )
//        }
//    }

//    fun getNowPlayingMovies() {
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