package com.example.lastproject_pam.ui.viewmodel.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject_pam.model.Studio
import com.example.lastproject_pam.repository.StudioRepository
import kotlinx.coroutines.launch

class InsertStudioViewModel (private val Sdio: StudioRepository): ViewModel(){
    var uiState by mutableStateOf(InsertSdioUiState())
        private set

    fun updateInsertSdioState(insertUiEvent: InsertUiEvent) {
        uiState = InsertSdioUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertSdio() {
        viewModelScope.launch {
            try {
                Sdio.insertStudio(uiState.insertUiEvent.toSdio())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertSdioUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_studio: Int = 0,
    val nama_studio: String = "",
    val kapasitas: String = "",
)

fun InsertUiEvent.toSdio(): Studio = Studio(
    id_studio = id_studio,
    nama_studio = nama_studio,
    kapasitas = kapasitas
)

fun Studio.toUiStateSdio(): InsertSdioUiState = InsertSdioUiState(
    insertUiEvent = tolnsertUiEvent()
)

fun Studio.tolnsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_studio = id_studio,
    nama_studio = nama_studio,
    kapasitas = kapasitas
)