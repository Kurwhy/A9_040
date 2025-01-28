package com.example.lastproject_pam.ui.viewmodel.film

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastproject_pam.model.Film
import com.example.lastproject_pam.repository.FilmRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeFlmUiState {
    data class Success(val film: List<Film>) : HomeFlmUiState()
    object Error : HomeFlmUiState()
    object Loading : HomeFlmUiState()
}


class HomeFilmViewModel (private val flm: FilmRepository): ViewModel() {
    var filmUiState: HomeFlmUiState by mutableStateOf(HomeFlmUiState.Loading)
        private set

    init {
        getFlm()
    }

    fun getFlm() {
        viewModelScope.launch {
            filmUiState = HomeFlmUiState.Loading
            filmUiState = try {
                HomeFlmUiState.Success(flm.getFilm().data)
            } catch (e: IOException) {
                HomeFlmUiState.Error
            } catch (e: HttpException) {
                HomeFlmUiState.Error
            }
        }
    }

    fun deleteFilm(id_film: Int) {
        viewModelScope.launch {
            try {
                flm.deleteFilm(id_film)
            } catch (e: IOException) {
                HomeFlmUiState.Error
            } catch (e: HttpException) {
                HomeFlmUiState.Error
            }
        }
    }
}