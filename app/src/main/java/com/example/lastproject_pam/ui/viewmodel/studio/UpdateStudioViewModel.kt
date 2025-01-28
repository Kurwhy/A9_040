package com.example.lastproject_pam.ui.viewmodel.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject_pam.repository.StudioRepository
import com.example.lastproject_pam.ui.view.studio.DestinasiUpdateSdio
import kotlinx.coroutines.launch

class UpdateStudioViewModel (
    savedStateHandle: SavedStateHandle,
    private val Sdio: StudioRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertSdioUiState())
        private set

    private val _id_studio: Int = checkNotNull(savedStateHandle[DestinasiUpdateSdio.ID_STUDIO])

    init {
        viewModelScope.launch {
            updateUiState = Sdio.getStudiobyId(_id_studio)
                .toUiStateSdio()
        }
    }

    fun updateInsertSdioState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertSdioUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateSdio(){
        viewModelScope.launch {
            try {
                Sdio.updateStudio(_id_studio, updateUiState.insertUiEvent.toSdio())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}