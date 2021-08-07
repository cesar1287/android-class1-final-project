package com.github.cesar1287.class1dhfinalproject.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    val tmdbApi : TMDBApi = getTMDBApiClient().create(TMDBApi::class.java)

    fun getTMDBApiClient() : Retrofit {
        return Retrofit.Builder()
                //.baseUrl(BuildConfig.BASE_URL)
                .baseUrl("https://api.themoviedb.org/3/")
                //.client(getInterceptorClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

//    private fun getInterceptorClient(): OkHttpClient {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        if (BuildConfig.DEBUG) {
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        }
//
//        val interceptor = OkHttpClient.Builder()
//            .connectTimeout(5, TimeUnit.SECONDS)
//            .readTimeout(10, TimeUnit.SECONDS)
//            .writeTimeout(10, TimeUnit.SECONDS)
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor { chain ->
//                val url = chain.request().url.newBuilder()
//                    .addQueryParameter(API_TOKEN_KEY, API_TOKEN)
//                    .addQueryParameter(QUERY_PARAM_LANGUAGE_LABEL, queryParamLanguageValue())
//                    .addQueryParameter(QUERY_PARAM_REGION_LABEL, QUERY_PARAM_REGION_VALUE)
//                    .build()
//                val newRequest = chain.request().newBuilder().url(url).build()
//                chain.proceed(newRequest)
//            }
//        return interceptor.build()
//    }
}