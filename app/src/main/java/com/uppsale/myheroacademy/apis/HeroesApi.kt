package com.uppsale.myheroacademy.apis

import com.uppsale.myheroacademy.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroesApi {
    @GET("{id}")
    suspend fun getHeroById(@Path("id") id: Int): Response<Hero>

    @GET("search/{name}")
    suspend fun getFilteredHeroesByName(@Path("name") name: String): Response<NameFilteredResponse>

    @GET("{id}/powerstats")
    suspend fun getHeroPowerstats(@Path("id") id: Int): Response<Powerstats>

    @GET("{id}/appearance")
    suspend fun getHeroAppearance(@Path("id") id: Int): Response<Appearance>

    @GET("{id}/connections")
    suspend fun getHeroConnections(@Path("id") id: Int): Response<Connections>


}