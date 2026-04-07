package com.example.eatik.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.eatik.data.MenuRepository
import com.example.eatik.data.Result
import com.example.eatik.data.remote.response.MenuResponseItem
import com.example.eatik.utils.Event
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainViewModel(private val repository: MenuRepository) : ViewModel() {

    private val _refreshTrigger = MutableLiveData<Unit>()
    
    val menuResult: LiveData<Result<List<MenuResponseItem>>> = _refreshTrigger.switchMap {
        repository.getMenuWithResult()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    init {
        refresh()
    }

    fun refresh() {
        _refreshTrigger.value = Unit
    }

    fun saveMenu(id: Int?, nama: RequestBody, harga: RequestBody, deskripsi: RequestBody, kategori: RequestBody, status: RequestBody, foto: MultipartBody.Part?) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = if (id != null) {
                    repository.updateMenu(id, nama, harga, deskripsi, kategori, status, foto)
                } else {
                    repository.createMenu(nama, harga, deskripsi, kategori, status, foto)
                }

                if (response.isSuccessful) {
                    _snackbarText.value = Event("Berhasil menyimpan data!")
                    refresh()
                } else {
                    _snackbarText.value = Event("Gagal: ${response.message()}")
                }
            } catch (e: Exception) {
                _snackbarText.value = Event("Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMenu(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.deleteMenu(id)
                if (response.isSuccessful) {
                    _snackbarText.value = Event("Data berhasil dihapus")
                    refresh()
                } else {
                    _snackbarText.value = Event("Gagal menghapus")
                }
            } catch (e: Exception) {
                _snackbarText.value = Event("Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}