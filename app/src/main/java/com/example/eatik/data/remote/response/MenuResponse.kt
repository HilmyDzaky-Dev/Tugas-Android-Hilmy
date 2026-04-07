package com.example.eatik.data.remote.response

import com.google.gson.annotations.SerializedName

data class MenuResponse(
	@field:SerializedName("MenuResponse")
	val menuResponse: List<MenuResponseItem>
)

data class MenuResponseItem(
	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: Int, // Diubah dari Any ke Int agar tidak error saat parsing

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("kategori")
	val kategori: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("status")
	val status: String
)
