package com.example.lastproject_pam.ui.viewmodel.film

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject_pam.repository.FilmRepository
import com.example.lastproject_pam.ui.view.film.DestinasiUpdateFlm
import kotlinx.coroutines.launch

class UpdateFilmViewModel (
    savedStateHandle: SavedStateHandle,
    private val flm: FilmRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertFilmUiState())
        private set

    private val _id_film: Int = checkNotNull(savedStateHandle[DestinasiUpdateFlm.ID_FILM])

    init {
        viewModelScope.launch {
            updateUiState = flm.getFilmbyId(_id_film)
                .toUiStateFlm()
        }
    }

    fun updateInsertFlmState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertFilmUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateFlm(){
        viewModelScope.launch {
            try {
                flm.updateFilm(_id_film, updateUiState.insertUiEvent.toFlm())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}