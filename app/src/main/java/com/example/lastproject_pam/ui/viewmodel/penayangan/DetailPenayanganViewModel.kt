package com.example.lastproject_pam.ui.viewmodel.penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastproject_pam.model.Penayangan
import com.example.lastproject_pam.repository.PenayanganRepository
import com.example.lastproject_pam.ui.view.penayangan.DestinasiDetailPenayangan
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailPenayanganUiState {
    data class Success(val penayangan: Penayangan) : DetailPenayanganUiState()
    object Error : DetailPenayanganUiState()
    object Loading : DetailPenayanganUiState()
}

class DetailPenayanganViewModel(
    savedStateHandle: SavedStateHandle,
    private val Pnygn: PenayanganRepository
) : ViewModel() {

    var penayanganDetailState: DetailPenayanganUiState by mutableStateOf(DetailPenayanganUiState.Loading)
        private set

    private val _id_penayangan: Int = checkNotNull(savedStateHandle[DestinasiDetailPenayangan.ID_PENAYANGAN])

    init {
        getPenayanganbyId()
    }

    fun getPenayanganbyId() {
        viewModelScope.launch {
            penayanganDetailState = DetailPenayanganUiState.Loading
            penayanganDetailState = try {
                val penayangan = Pnygn.getPenayanganbyId(_id_penayangan)
                DetailPenayanganUiState.Success(penayangan)
            } catch (e: IOException) {
                DetailPenayanganUiState.Error
            } catch (e: HttpException) {
                DetailPenayanganUiState.Error
            }
        }
    }
}
