package com.example.lastproject_pam.ui.viewmodel.penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastproject_pam.model.Penayangan
import com.example.lastproject_pam.repository.PenayanganRepository
import kotlinx.coroutines.launch
import okio.IOException


sealed class HomePnygnUiState {
    data class Success(val penayangan: List<Penayangan>) : HomePnygnUiState()
    object Error : HomePnygnUiState()
    object Loading : HomePnygnUiState()
}

class HomePenayanganViewModel (private val Pnygn: PenayanganRepository): ViewModel() {
    var penyanganUiState: HomePnygnUiState by mutableStateOf(HomePnygnUiState.Loading)
        private set

    init {
        getPnygn()
    }

    fun getPnygn() {
        viewModelScope.launch {
            penyanganUiState = HomePnygnUiState.Loading
            penyanganUiState = try {
                HomePnygnUiState.Success(Pnygn.getPenayangan().data)
            } catch (e: IOException) {
                HomePnygnUiState.Error
            } catch (e: HttpException) {
                HomePnygnUiState.Error
            }
        }
    }

    fun deletePnygn(id_penayangan: Int) {
        viewModelScope.launch {
            try {
                Pnygn.deletePenayangan(id_penayangan)
            } catch (e: IOException) {
                HomePnygnUiState.Error
            } catch (e: HttpException) {
                HomePnygnUiState.Error
            }
        }
    }
}