package com.uppsale.myheroacademy

import com.uppsale.myheroacademy.apis.HeroesApi
import javax.inject.Inject

class MainRepository @Inject constructor(app:App) {
    @Inject
    lateinit var heroApi:HeroesApi

    suspend fun getHeroById(id:Int) = heroApi.getHeroById(id)
    suspend fun getFilteredHeroesByName(name:String) = heroApi.getFilteredHeroesByName(name)
    suspend fun getHeroPowerstats(id:Int) = heroApi.getHeroPowerstats(id)
    suspend fun getHeroAppearance(id:Int) = heroApi.getHeroAppearance(id)
    suspend fun getHeroConnections(id:Int) = heroApi.getHeroConnections(id)

}