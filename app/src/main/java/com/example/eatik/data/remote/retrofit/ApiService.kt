package com.example.eatik.data.remote.retrofit

import com.example.eatik.data.remote.response.MenuResponseItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("menu")
    suspend fun getMenu(): List<MenuResponseItem>

    @Multipart
    @POST("menu")
    suspend fun createMenu(
        @Part("nama") nama: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("kategori") kategori: RequestBody,
        @Part("status") status: RequestBody,
        @Part foto: MultipartBody.Part?
    ): Response<Unit>

    @Multipart
    @POST("menu/{id}")
    suspend fun updateMenu(
        @Path("id") id: Int,
        @Part("nama") nama: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("kategori") kategori: RequestBody,
        @Part("status") status: RequestBody,
        @Part foto: MultipartBody.Part?
    ): Response<Unit>

    @DELETE("menu/{id}")
    suspend fun deleteMenu(
        @Path("id") id: Int
    ): Response<Unit>
}