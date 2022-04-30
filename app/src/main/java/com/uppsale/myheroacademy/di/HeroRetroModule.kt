package com.uppsale.myheroacademy.di

import com.uppsale.myheroacademy.apis.HeroesApi
import com.uppsale.myheroacademy.util.Consts
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class HeroRetroModule {

    @Singleton
    @Provides
    fun retrofitInstance():Retrofit{
        val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(Consts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun getApi():HeroesApi{
        return retrofitInstance().create(HeroesApi::class.java)
    }
}