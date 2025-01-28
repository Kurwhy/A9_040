package com.example.lastproject_pam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject_pam.model.Penayangan
import com.example.lastproject_pam.repository.PenayanganRepository
import com.example.lastproject_pam.repository.TiketRepository
import com.example.lastproject_pam.ui.view.tiket.DestinasiUpdateTkit
import kotlinx.coroutines.launch

class UpdateTiketViewModel (
    savedStateHandle: SavedStateHandle,
    private val Tkit: TiketRepository,
    private val Pnygn: PenayanganRepository,

    ): ViewModel(){
    var updateUiState by mutableStateOf(InsertTkitUiState())
        private set
    var penayanganList by mutableStateOf<List<Penayangan>>(listOf())
        private set

    private val _id_tiket: Int = checkNotNull(savedStateHandle[DestinasiUpdateTkit.ID_TIKET])

    init {
        viewModelScope.launch {
            loadPenayangan()
            loadTiket(_id_tiket)
        }
    }

    private suspend fun loadPenayangan() {
        penayanganList = Pnygn.getAllPenayangans()
    }

    private fun loadTiket(id_tiket: Int) {
        viewModelScope.launch {
            try {
                val tiket = Tkit.getTiketbyId(id_tiket)
                updateUiState = tiket.toUiStateTkit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertTkitState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertTkitUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateTkit(){
        viewModelScope.launch {
            try {
                Tkit.updateTiket(_id_tiket, updateUiState.insertUiEvent.toTkit())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}