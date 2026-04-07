package com.example.eatik.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatik.R
import com.example.eatik.data.remote.response.MenuResponseItem
import com.example.eatik.databinding.ItemMenuBinding

class MenuAdapter(
    private val listMenu: List<MenuResponseItem>,
    private val onEditClick: (MenuResponseItem) -> Unit,
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = listMenu[position]
        holder.binding.apply {
            tvNama.text = item.nama
            tvHarga.text = "Rp ${item.harga}"
            tvDeskripsi.text = item.deskripsi
            tvKategori.text = item.kategori
            tvStatus.text = item.status

            if (item.status.contains("Tersedia", ignoreCase = true)) {
                tvStatus.setTextColor(root.context.getColor(android.R.color.holo_green_dark))
            } else {
                tvStatus.setTextColor(root.context.getColor(android.R.color.holo_red_dark))
            }

            // LOAD GAMBAR DARI PUSAT IP (ApiConfig)
            Glide.with(root.context)
                .load("http://192.168.0.238:8080/uploads/${item.foto}")
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(ivMenu)
        }
    }
    override fun getItemCount(): Int = listMenu.size
}
