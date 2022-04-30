package com.uppsale.myheroacademy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.uppsale.myheroacademy.viewModels.MainViewModel
import com.uppsale.myheroacademy.viewModels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelFactory = MainViewModelFactory(application as App)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
    }
}