package com.example.eatik.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.eatik.data.local.entity.MenuEntity
import com.example.eatik.data.local.room.MenuDao
import com.example.eatik.data.remote.response.MenuResponseItem
import com.example.eatik.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class MenuRepository(
    private val apiService: ApiService,
    private val menuDao: MenuDao
) {

    fun getMenuWithResult(): LiveData<Result<List<MenuResponseItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getMenu()
            val entities = response.map { item ->
                MenuEntity(
                    id = item.id,
                    nama = item.nama,
                    harga = item.harga,
                    foto = item.foto,
                    kategori = item.kategori,
                    deskripsi = item.deskripsi,
                    status = item.status
                )
            }
            menuDao.deleteAll()
            menuDao.insertMenu(entities)
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Gagal memperbarui data"))
        }

        val source: LiveData<Result<List<MenuResponseItem>>> = menuDao.getAllMenu().map { entities ->
            Result.Success(entities.map { entity ->
                MenuResponseItem(
                    id = entity.id,
                    nama = entity.nama,
                    harga = entity.harga,
                    foto = entity.foto,
                    kategori = entity.kategori,
                    deskripsi = entity.deskripsi,
                    status = entity.status
                )
            })
        }
        emitSource(source)
    }

    suspend fun createMenu(
        nama: RequestBody,
        harga: RequestBody,
        deskripsi: RequestBody,
        kategori: RequestBody,
        status: RequestBody,
        foto: MultipartBody.Part?
    ): Response<Unit> {
        return apiService.createMenu(nama, harga, deskripsi, kategori, status, foto)
    }

    suspend fun updateMenu(
        id: Int,
        nama: RequestBody,
        harga: RequestBody,
        deskripsi: RequestBody,
        kategori: RequestBody,
        status: RequestBody,
        foto: MultipartBody.Part?
    ): Response<Unit> {
        return apiService.updateMenu(id, nama, harga, deskripsi, kategori, status, foto)
    }

    suspend fun deleteMenu(id: Int): Response<Unit> {
        return apiService.deleteMenu(id)
    }
}