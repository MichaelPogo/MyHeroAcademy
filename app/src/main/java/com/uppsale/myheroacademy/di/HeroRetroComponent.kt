package com.uppsale.myheroacademy.di

import com.uppsale.myheroacademy.MainRepository
import com.uppsale.myheroacademy.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HeroRetroModule::class])
interface HeroRetroComponent {
    fun getMainRepository():MainRepository
    fun inject(mainViewModel: MainViewModel)
}