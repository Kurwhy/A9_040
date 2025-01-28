package com.example.lastproject_pam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastproject_pam.model.Tiket
import com.example.lastproject_pam.repository.TiketRepository
import com.example.lastproject_pam.ui.view.tiket.DestinasiDetailTiket
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailTiketUiState {
    data class Success(val tiket: Tiket) : DetailTiketUiState()
    object Error : DetailTiketUiState()
    object Loading : DetailTiketUiState()
}

class DetailTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val Tkit: TiketRepository
) : ViewModel() {

    var tiketDetailState: DetailTiketUiState by mutableStateOf(DetailTiketUiState.Loading)
        private set

    private val _id_tiket: Int = checkNotNull(savedStateHandle[DestinasiDetailTiket.ID_TIKET])

    init {
        getTiketbyId()
    }

    fun getTiketbyId() {
        viewModelScope.launch {
            tiketDetailState = DetailTiketUiState.Loading
            tiketDetailState = try {
                val tiket = Tkit.getTiketbyId(_id_tiket)
                DetailTiketUiState.Success(tiket)
            } catch (e: IOException) {
                DetailTiketUiState.Error
            } catch (e: HttpException) {
                DetailTiketUiState.Error
            }
        }
    }
}
