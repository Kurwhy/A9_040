package com.example.lastproject_pam.ui.viewmodel.film

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastproject_pam.model.Film
import com.example.lastproject_pam.repository.FilmRepository
import com.example.lastproject_pam.ui.view.film.DestinasiDetailFilm
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailFilmUiState {
    data class Success(val film: Film) : DetailFilmUiState()
    object Error : DetailFilmUiState()
    object Loading : DetailFilmUiState()
}

class DetailFilmViewModel(
    savedStateHandle: SavedStateHandle,
    private val flm: FilmRepository
) : ViewModel() {

    var filmDetailState: DetailFilmUiState by mutableStateOf(DetailFilmUiState.Loading)
        private set

    private val _id_film: Int = checkNotNull(savedStateHandle[DestinasiDetailFilm.ID_FILM])

    init {
        getFilmbyId()
    }

    fun getFilmbyId() {
        viewModelScope.launch {
            filmDetailState = DetailFilmUiState.Loading
            filmDetailState = try {
                val film = flm.getFilmbyId(_id_film)
                DetailFilmUiState.Success(film)
            } catch (e: IOException) {
                DetailFilmUiState.Error
            } catch (e: HttpException) {
                DetailFilmUiState.Error
            }
        }
    }
}
