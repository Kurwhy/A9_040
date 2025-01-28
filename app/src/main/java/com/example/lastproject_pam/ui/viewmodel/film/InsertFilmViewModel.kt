package com.example.lastproject_pam.ui.viewmodel.film

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject_pam.model.Film
import com.example.lastproject_pam.repository.FilmRepository
import kotlinx.coroutines.launch

class InsertFilmViewModel (private val flm: FilmRepository): ViewModel(){
    var uiState by mutableStateOf(InsertFilmUiState())
        private set

    fun updateInsertFlmState(insertUiEvent: InsertUiEvent) {
        uiState = InsertFilmUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertFlm() {
        viewModelScope.launch {
            try {
                flm.insertFilm(uiState.insertUiEvent.toFlm())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertFilmUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_film: Int = 0,
    val judul_film: String = "",
    val durasi: String = "",
    val deskripsi: String = "",
    val genre: String = "",
    val rating_usia: String = "",
)

fun InsertUiEvent.toFlm(): Film = Film(
    id_film = id_film,
    judul_film = judul_film,
    durasi = durasi,
    deskripsi = deskripsi,
    genre = genre,
    rating_usia = rating_usia
)

fun Film.toUiStateFlm(): InsertFilmUiState = InsertFilmUiState(
    insertUiEvent = tolnsertUiEvent()
)

fun Film.tolnsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_film = id_film,
    judul_film = judul_film,
    durasi = durasi,
    deskripsi = deskripsi,
    genre = genre,
    rating_usia = rating_usia
)