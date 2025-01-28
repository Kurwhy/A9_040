package com.example.lastproject_pam.ui.viewmodel.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastproject_pam.model.Studio
import com.example.lastproject_pam.repository.StudioRepository
import kotlinx.coroutines.launch
import okio.IOException


sealed class HomeSdioUiState {
    data class Success(val studio: List<Studio>) : HomeSdioUiState()
    object Error : HomeSdioUiState()
    object Loading : HomeSdioUiState()
}

class HomeStudioViewModel (private val Sdio: StudioRepository): ViewModel() {
    var studioUiState: HomeSdioUiState by mutableStateOf(HomeSdioUiState.Loading)
        private set

    init {
        getSdio()
    }

    fun getSdio() {
        viewModelScope.launch {
            studioUiState = HomeSdioUiState.Loading
            studioUiState = try {
                HomeSdioUiState.Success(Sdio.getStudio().data)
            } catch (e: IOException) {
                HomeSdioUiState.Error
            } catch (e: HttpException) {
                HomeSdioUiState.Error
            }
        }
    }

    fun deleteSdio(id_studio: Int) {
        viewModelScope.launch {
            try {
                Sdio.deleteStudio(id_studio)
            } catch (e: IOException) {
                HomeSdioUiState.Error
            } catch (e: HttpException) {
                HomeSdioUiState.Error
            }
        }
    }
}