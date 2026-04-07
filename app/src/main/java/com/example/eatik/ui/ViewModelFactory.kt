package com.example.eatik.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eatik.data.MenuRepository
import com.example.eatik.data.local.room.MenuDatabase
import com.example.eatik.data.remote.retrofit.ApiConfig
import com.example.eatik.di.Injection.provideRepository

class ViewModelFactory(private val repository: MenuRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(provideRepository(context))
            }.also { instance = it }


    }
}