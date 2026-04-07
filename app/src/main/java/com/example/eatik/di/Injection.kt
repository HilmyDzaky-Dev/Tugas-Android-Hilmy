package com.example.eatik.di

import android.content.Context
import com.example.eatik.data.MenuRepository
import com.example.eatik.data.local.room.MenuDatabase
import com.example.eatik.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): MenuRepository {
        val apiService = ApiConfig.getApiService()
        val database = MenuDatabase.getDatabase(context)
        val dao = database.menuDao()
        return MenuRepository(apiService, dao)
    }
}