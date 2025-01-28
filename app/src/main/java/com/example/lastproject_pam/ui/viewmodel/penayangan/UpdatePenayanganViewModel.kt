package com.example.lastproject_pam.ui.viewmodel.penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject_pam.model.Film
import com.example.lastproject_pam.model.Studio
import com.example.lastproject_pam.repository.FilmRepository
import com.example.lastproject_pam.repository.PenayanganRepository
import com.example.lastproject_pam.repository.StudioRepository
import com.example.lastproject_pam.ui.view.penayangan.DestinasiUpdatePnygn
import kotlinx.coroutines.launch

class UpdatePenayanganViewModel(
    savedStateHandle: SavedStateHandle,
    private val Pnygn: PenayanganRepository,
    private val Sdio: StudioRepository,
    private val Flm: FilmRepository
) : ViewModel() {
    var updateUiState by mutableStateOf(InsertPnygnUiState())
        private set
    var studioList by mutableStateOf<List<Studio>>(listOf())
        private set
    var filmList by mutableStateOf<List<Film>>(listOf())
        private set

    private val _id_penayangan: Int = checkNotNull(savedStateHandle[DestinasiUpdatePnygn.ID_PENAYANGAN])

    init {
        viewModelScope.launch {
            loadStudios()
            loadFilms()
            loadPenayangan(_id_penayangan)
        }
    }

    private suspend fun loadStudios() {
        studioList = Sdio.getAllStudios()
    }

    private suspend fun loadFilms() {
        filmList = Flm.getAllFilms()
    }

    private fun loadPenayangan(id_penayangan: Int) {
        viewModelScope.launch {
            try {
                val penayangan = Pnygn.getPenayanganbyId(id_penayangan)
                updateUiState = penayangan.toUiStatePnygn()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertPnygnState(insertUiEvent: InsertUiEvent) {
        updateUiState = InsertPnygnUiState(insertUiEvent = insertUiEvent)
    }

    fun updatePnygn() {
        viewModelScope.launch {
            try {
                Pnygn.updatePenayangan(_id_penayangan, updateUiState.insertUiEvent.toPnygn())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
