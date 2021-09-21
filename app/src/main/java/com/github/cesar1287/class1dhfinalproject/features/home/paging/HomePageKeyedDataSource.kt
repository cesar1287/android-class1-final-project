package com.github.cesar1287.class1dhfinalproject.features.home.paging

import androidx.paging.PageKeyedDataSource
import com.github.cesar1287.class1dhfinalproject.features.home.repository.HomeRepository
import com.github.cesar1287.class1dhfinalproject.features.home.usecase.HomeUseCase
import com.github.cesar1287.class1dhfinalproject.model.NowPlaying
import com.github.cesar1287.class1dhfinalproject.model.Result
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Home.FIRST_PAGE
import com.github.cesar1287.class1dhfinalproject.utils.ResponseApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomePageKeyedDataSource(
    private val homeRepository: HomeRepository,
    private val homeUseCase: HomeUseCase
) : PageKeyedDataSource<Int, Result>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        CoroutineScope(IO).launch {
            val movies: List<Result> = callNowPlayingMoviesApi(FIRST_PAGE)
            homeUseCase.saveMoviesDb(movies)
            callback.onResult(movies, null, FIRST_PAGE + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        loadData(params.key, params.key - 1, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        loadData(params.key, params.key + 1, callback)
    }

    private fun loadData(page: Int, nextPage: Int, callback: LoadCallback<Int, Result>) {
        CoroutineScope(IO).launch {
            val movies: List<Result> = callNowPlayingMoviesApi(page)
            homeUseCase.saveMoviesDb(movies)
            callback.onResult(movies, nextPage)
        }
    }

    private suspend fun callNowPlayingMoviesApi(page: Int): List<Result> {
        return when (
            val response = homeRepository.getNowPlayingMovies(page)
        ) {
            is ResponseApi.Success -> {
                val list = response.data as? NowPlaying
                homeUseCase.setupMoviesList(list)
            }
            is ResponseApi.Error -> {
                listOf()
            }
        }
    }
}