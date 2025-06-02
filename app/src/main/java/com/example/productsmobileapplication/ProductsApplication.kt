package com.example.productsmobileapplication

import android.app.Application
import com.example.productsmobileapplication.di.ViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel

class ProductsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any dependencies here
    }
}