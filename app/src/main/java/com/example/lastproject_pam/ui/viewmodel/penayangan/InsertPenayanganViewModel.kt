package com.example.lastproject_pam.ui.viewmodel.penayangan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject_pam.model.Film
import com.example.lastproject_pam.model.Penayangan
import com.example.lastproject_pam.model.Studio
import com.example.lastproject_pam.repository.FilmRepository
import com.example.lastproject_pam.repository.PenayanganRepository
import com.example.lastproject_pam.repository.StudioRepository
import kotlinx.coroutines.launch

class InsertPenayanganViewModel (
    private val Pnygn: PenayanganRepository,
    private val Sdio: StudioRepository,
    private val Flm: FilmRepository
): ViewModel(){
    var uiState by mutableStateOf(InsertPnygnUiState())
        private set
    var studioList by mutableStateOf<List<Studio>>(listOf())
        private set
    var filmList by mutableStateOf<List<Film>>(listOf())
        private set

    init {
        viewModelScope.launch {
            loadStudios()
            loadFilm()
        }
    }
    private suspend fun loadStudios() {
        studioList = Sdio.getAllStudios()
        studioList.forEach { studio ->
            println("Studio: ${studio.id_studio}")
        }
    }

    private suspend fun loadFilm() {
        filmList = Flm.getAllFilms()
        filmList.forEach { film ->
            println("Film: ${film.id_film}")
        }
    }

    fun updateInsertPnygnState(insertUiEvent: InsertUiEvent) {
        uiState = InsertPnygnUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPnygn() {
        viewModelScope.launch {
            try {
                Pnygn.insertPenayangan(uiState.insertUiEvent.toPnygn())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPnygnUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_penayangan: Int = 0,
    val id_film: Int = 0,
    val id_studio: Int = 0,
    val tanggal_penayangan: String = "",
    val harga_tiket: Int = 0,
)

fun InsertUiEvent.toPnygn(): Penayangan = Penayangan(
    id_penayangan = id_penayangan,
    id_film = id_film,
    id_studio = id_studio,
    tanggal_penayangan = tanggal_penayangan,
    harga_tiket = harga_tiket,
)

fun Penayangan.toUiStatePnygn(): InsertPnygnUiState = InsertPnygnUiState(
    insertUiEvent = tolnsertUiEvent()
)

fun Penayangan.tolnsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_penayangan = id_penayangan,
    id_film = id_film,
    id_studio = id_studio,
    tanggal_penayangan = tanggal_penayangan,
    harga_tiket = harga_tiket,
)