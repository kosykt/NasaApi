package com.example.nasaapi.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiHolder {

    private val baseUrl = "https://api.nasa.gov/"

    val retrofitService by lazy {
        getRetrofit()
            .create<RetrofitService>()
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(provideOkHttpClient(provideOkHttpInterceptor()))
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    private fun provideOkHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

}