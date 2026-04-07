package com.example.eatik.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eatik.data.local.entity.MenuEntity

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: List<MenuEntity>)

    @Query("SELECT * FROM menu")
    fun getAllMenu(): LiveData<List<MenuEntity>>

    @Query("DELETE FROM menu")
    suspend fun deleteAll()
}