package com.example.lastproject_pam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject_pam.model.Penayangan
import com.example.lastproject_pam.model.Tiket
import com.example.lastproject_pam.repository.PenayanganRepository
import com.example.lastproject_pam.repository.TiketRepository
import kotlinx.coroutines.launch

class InsertTiketViewModel (
    private val Tkit: TiketRepository,
    private val Pnygn: PenayanganRepository
): ViewModel(){
    var uiState by mutableStateOf(InsertTkitUiState())
        private set
    var penayanganList by mutableStateOf<List<Penayangan>>(listOf())
        private set

    init {
        viewModelScope.launch {
            loadPenayangan()
        }
    }
    private suspend fun loadPenayangan() {
        penayanganList = Pnygn.getAllPenayangans()
        penayanganList.forEach { penayangan ->
            println("Penayangan: ${penayangan.id_penayangan}")
        }
    }

    fun updateInsertTkitState(insertUiEvent: InsertUiEvent) {
        val selectedPenayangan = penayanganList.find { it.id_penayangan == insertUiEvent.id_penayangan }
        val hargaTiket = selectedPenayangan?.harga_tiket ?: 0
        val jumlahTiket = insertUiEvent.jumlah_tiket
        val totalHarga = jumlahTiket * hargaTiket

        uiState = uiState.copy(
            insertUiEvent = insertUiEvent.copy(total_harga = totalHarga)
        )
    }

    suspend fun insertTkit() {
        viewModelScope.launch {
            try {
                Tkit.insertTiket(uiState.insertUiEvent.toTkit())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTkitUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_tiket: Int = 0,
    val id_penayangan: Int = 0,
    val jumlah_tiket: Int = 0,
    val total_harga: Int = 0,
    val status_pembayaran: String = "",
)

fun InsertUiEvent.toTkit(): Tiket = Tiket(
    id_tiket = id_tiket,
    id_penayangan = id_penayangan,
    jumlah_tiket = jumlah_tiket,
    total_harga = total_harga,
    status_pembayaran = status_pembayaran
)

fun Tiket.toUiStateTkit(): InsertTkitUiState = InsertTkitUiState(
    insertUiEvent = tolnsertUiEvent()
)

fun Tiket.tolnsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_tiket = id_tiket,
    id_penayangan = id_penayangan,
    jumlah_tiket = jumlah_tiket,
    total_harga = total_harga,
    status_pembayaran = status_pembayaran
)