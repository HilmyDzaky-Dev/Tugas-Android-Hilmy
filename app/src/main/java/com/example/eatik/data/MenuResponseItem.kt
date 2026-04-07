package com.example.eatik.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "menu")
data class MenuResponseItem(
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("harga")
    val harga: Int,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("kategori")
    val kategori: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("foto")
    val foto: String
)