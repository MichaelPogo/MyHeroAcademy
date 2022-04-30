package com.uppsale.myheroacademy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uppsale.myheroacademy.declarations.notifyObserver
import com.uppsale.myheroacademy.models.Appearance
import com.uppsale.myheroacademy.models.Connections
import com.uppsale.myheroacademy.models.FavHeroes.GROOT
import com.uppsale.myheroacademy.models.FavHeroes.DR_STRANGE
import com.uppsale.myheroacademy.models.FavHeroes.GAMORA
import com.uppsale.myheroacademy.models.Hero
import com.uppsale.myheroacademy.models.Powerstats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import javax.inject.Inject

class MainViewModel(app:App): ViewModel() {
    private val TAG = "MainViewModel"
    val favoriteHeroIds = listOf<Int>(GROOT.id,DR_STRANGE.id,GAMORA.id)
    private val favoriteHeroes:ArrayList<Hero>;
    @Inject
    lateinit var mainRepository: MainRepository
    private var mHeroes:MutableLiveData<ArrayList<Hero>>
    private var mIsFetching:MutableLiveData<Boolean> = MutableLiveData(false)
    private var mPowerstats: MutableLiveData<Powerstats>
    private var mAppearance: MutableLiveData<Appearance>
    private var mConnections: MutableLiveData<Connections>
    init {
        app.heroRetroComponent.inject(this)
        mHeroes = MutableLiveData(ArrayList());
        mPowerstats = MutableLiveData();
        mAppearance = MutableLiveData();
        mConnections = MutableLiveData();
        favoriteHeroes = ArrayList();
        fetchFavHeroes()
    }

    //create livedata references to restrict modifying from outside
    val heroes:LiveData<ArrayList<Hero>> = mHeroes
    val isFetching:LiveData<Boolean> = mIsFetching

    private fun fetchFavHeroes()=viewModelScope.launch() {
        mIsFetching.postValue(true)
        favoriteHeroes.clear()
        favoriteHeroIds.forEach { id ->
            val response = mainRepository.getHeroById(id)
            if(response.isSuccessful){
                response.body()?.let { hero ->
                    favoriteHeroes.add(hero)
                }
            }else{
                Log.e(TAG, "failed to fetchFavHeroes: response code ${response.code()}")
            }
        }
        heroes.value?.addAll(favoriteHeroes)
        withContext(Dispatchers.Main){
            //extension fun to trigger observer after adding items to the list (observer won't be triggered if items added to the list one by one)
            mHeroes.notifyObserver()
            mIsFetching.postValue(false)
        }

    }

    fun fetchHeroBySearchName(name:String) = viewModelScope.launch {
        mIsFetching.postValue(true)
        val response = mainRepository.getFilteredHeroesByName(name)
        if(response.isSuccessful){
            response.body()?.let { heroesRes ->
                val tmpFavWithSearch = ArrayList<Hero>(favoriteHeroes)
                heroesRes.results?.let {
                    tmpFavWithSearch.addAll(it)
                }

                mHeroes.postValue(tmpFavWithSearch)
            }
            withContext(Dispatchers.Main){
                //extension fun to trigger observer after adding items to the list (observer won't be triggered if items added to the list one by one)
                mHeroes.notifyObserver()
                mIsFetching.postValue(false)
            }
        }else{
            Log.e(TAG, "failed to fetchHeroBySearchName: response code ${response.code()}")
        }
    }

    val powerstats:LiveData<Powerstats> = mPowerstats
    fun getHeroPowerStats(id:Int) = viewModelScope.launch {
        val response = mainRepository.getHeroPowerstats(id)
        if(response.isSuccessful){
            response.body()?.let {
                mPowerstats.postValue(it)
            }
        }else{
            Log.e(TAG, "failed to getHeroPowerStats: response code ${response.code()}")
        }
    }

    val appearance:LiveData<Appearance> = mAppearance

    fun getHeroAppearance(id:Int) = viewModelScope.launch {
        val response = mainRepository.getHeroAppearance(id)
        if(response.isSuccessful){
            response.body()?.let {
                mAppearance.postValue(it)
            }
        }else{
            Log.e(TAG, "failed to getHeroAppearance: response code ${response.code()}")
        }
    }

    val connections:LiveData<Connections> = mConnections

    fun getHeroConnections(id:Int) = viewModelScope.launch {
        val response = mainRepository.getHeroConnections(id)
        if(response.isSuccessful){
            response.body()?.let {
                mConnections.postValue(it)
            }
        }else{
            Log.e(TAG, "failed to getHeroConnections: response code ${response.code()}")
        }
    }
}