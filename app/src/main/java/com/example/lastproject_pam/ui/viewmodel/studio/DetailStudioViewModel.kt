package com.example.lastproject_pam.ui.viewmodel.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastproject_pam.model.Studio
import com.example.lastproject_pam.repository.StudioRepository
import com.example.lastproject_pam.ui.view.studio.DestinasiDetailStudio
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailStudioUiState {
    data class Success(val studio: Studio) : DetailStudioUiState()
    object Error : DetailStudioUiState()
    object Loading : DetailStudioUiState()
}

class DetailStudioViewModel(
    savedStateHandle: SavedStateHandle,
    private val Sdio: StudioRepository
) : ViewModel() {

    var studioDetailState: DetailStudioUiState by mutableStateOf(DetailStudioUiState.Loading)
        private set

    private val _id_studio: Int = checkNotNull(savedStateHandle[DestinasiDetailStudio.ID_STUDIO])

    init {
        getStudiobyId()
    }

    fun getStudiobyId() {
        viewModelScope.launch {
            studioDetailState = DetailStudioUiState.Loading
            studioDetailState = try {
                val studio = Sdio.getStudiobyId(_id_studio)
                DetailStudioUiState.Success(studio)
            } catch (e: IOException) {
                DetailStudioUiState.Error
            } catch (e: HttpException) {
                DetailStudioUiState.Error
            }
        }
    }
}
