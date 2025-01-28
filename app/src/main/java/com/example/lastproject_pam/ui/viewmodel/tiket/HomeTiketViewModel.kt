package com.example.lastproject_pam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastproject_pam.model.Tiket
import com.example.lastproject_pam.repository.TiketRepository
import kotlinx.coroutines.launch
import okio.IOException


sealed class HomeTkitUiState {
    data class Success(val tiket: List<Tiket>) : HomeTkitUiState()
    object Error : HomeTkitUiState()
    object Loading : HomeTkitUiState()
}

class HomeTiketViewModel (private val Tkit: TiketRepository): ViewModel() {
    var tiketUiState: HomeTkitUiState by mutableStateOf(HomeTkitUiState.Loading)
        private set

    init {
        getTkit()
    }

    fun getTkit() {
        viewModelScope.launch {
            tiketUiState = HomeTkitUiState.Loading
            tiketUiState = try {
                HomeTkitUiState.Success(Tkit.getTiket().data)
            } catch (e: IOException) {
                HomeTkitUiState.Error
            } catch (e: HttpException) {
                HomeTkitUiState.Error
            }
        }
    }

    fun deleteTkit(id_tiket: Int) {
        viewModelScope.launch {
            try {
                Tkit.deleteTiket(id_tiket)
            } catch (e: IOException) {
                HomeTkitUiState.Error
            } catch (e: HttpException) {
                HomeTkitUiState.Error
            }
        }
    }
}