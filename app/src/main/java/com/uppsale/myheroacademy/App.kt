package com.uppsale.myheroacademy

import android.app.Application
import com.uppsale.myheroacademy.di.DaggerHeroRetroComponent
import com.uppsale.myheroacademy.di.HeroRetroComponent
import com.uppsale.myheroacademy.di.HeroRetroModule
import javax.inject.Inject

class App @Inject constructor() : Application() {
    lateinit var heroRetroComponent: HeroRetroComponent
    override fun onCreate() {
        super.onCreate()
        heroRetroComponent = DaggerHeroRetroComponent.builder().heroRetroModule(HeroRetroModule()).build()
    }
}