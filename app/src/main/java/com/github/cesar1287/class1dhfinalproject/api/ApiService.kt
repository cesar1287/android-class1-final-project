package com.github.cesar1287.class1dhfinalproject.api

import com.github.cesar1287.class1dhfinalproject.BuildConfig
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Api.API_TOKEN
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Api.API_TOKEN_KEY
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Api.HEADER_CONTENT_KEY
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Api.HEADER_CONTENT_VALUE
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Api.QUERY_PARAM_LANGUAGE_KEY
import com.github.cesar1287.class1dhfinalproject.utils.ConstantsApp.Api.QUERY_PARAM_LANGUAGE_VALUE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {

    val tmdbApi: TMDBApi = getTMDBApiClient().create(TMDBApi::class.java)

    fun getTMDBApiClient(): Retrofit {
        return Retrofit.Builder()
            //.baseUrl(BuildConfig.BASE_URL)
            .baseUrl("https://api.themoviedb.org/3/")
            .client(getInterceptorClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getInterceptorClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        val interceptor = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter(QUERY_PARAM_LANGUAGE_KEY, QUERY_PARAM_LANGUAGE_VALUE)
                    .build()
                val newRequest = chain.request().newBuilder().url(url).build()
                chain.proceed(newRequest)
            }
            .addInterceptor { chain ->
                val headers = chain.request().newBuilder()
                    .addHeader(HEADER_CONTENT_KEY, HEADER_CONTENT_VALUE)
                    .addHeader(API_TOKEN_KEY, "Bearer $API_TOKEN")
                val newRequest = headers.build()
                chain.proceed(newRequest)
            }
        return interceptor.build()
    }
}