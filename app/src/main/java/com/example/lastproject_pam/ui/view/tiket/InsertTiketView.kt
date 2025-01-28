package com.example.lastproject_pam.ui.view.tiket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastproject_pam.model.Penayangan
import com.example.lastproject_pam.ui.costumwidget.CoustumeTopAppBar
import com.example.lastproject_pam.ui.navigation.DestinasiNavigasi
import com.example.lastproject_pam.ui.viewmodel.PenyediaViewModel
import com.example.lastproject_pam.ui.viewmodel.tiket.InsertTiketViewModel
import com.example.lastproject_pam.ui.viewmodel.tiket.InsertTkitUiState
import com.example.lastproject_pam.ui.viewmodel.tiket.InsertUiEvent
import kotlinx.coroutines.launch

object DestinasiEntryTiket : DestinasiNavigasi {
    override val route = "item_entry_tiket"
    override val titleRes = "Entry Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTkitScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiEntryTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertTkitUiState = viewModel.uiState,
            penayanganList = viewModel.penayanganList,
            onTiketValueChange = viewModel::updateInsertTkitState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTkit()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertTkitUiState: InsertTkitUiState,
    onTiketValueChange: (InsertUiEvent) -> Unit,
    penayanganList: List<Penayangan>,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertTkitUiState.insertUiEvent,
            onValueChange = onTiketValueChange,
            penayanganList = penayanganList,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    onValueChange: (InsertUiEvent) -> Unit,
    penayanganList: List<Penayangan>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.id_tiket.toString(),
            onValueChange = {
                val intValue = it.toIntOrNull()
                if (intValue != null) {
                    onValueChange(insertUiEvent.copy(id_tiket = intValue))
                }
            },
            label = { Text("Id Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        PenayanganDropdown(
            penayanganList = penayanganList,
            selectedPenayanganId = insertUiEvent.id_penayangan,
            onPenayanganSelected = { selectedPenayanganId ->
                val selectedPenayangan = penayanganList.find { it.id_penayangan == selectedPenayanganId }
                val hargaTiket = selectedPenayangan?.harga_tiket ?: 0
                val totalHarga = insertUiEvent.jumlah_tiket * hargaTiket
                onValueChange(insertUiEvent.copy(id_penayangan = selectedPenayanganId, total_harga = totalHarga))
            }
        )

        OutlinedTextField(
            value = insertUiEvent.jumlah_tiket.toString(),
            onValueChange = {
                val jumlahTiket = it.toIntOrNull() ?: 0
                val selectedPenayangan = penayanganList.find { it.id_penayangan == insertUiEvent.id_penayangan }
                val hargaTiket = selectedPenayangan?.harga_tiket ?: 0
                val totalHarga = jumlahTiket * hargaTiket
                onValueChange(insertUiEvent.copy(jumlah_tiket = jumlahTiket, total_harga = totalHarga))
            },
            label = { Text("Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.total_harga.toString(),
            onValueChange = {},
            label = { Text("Total Harga") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true
        )

        Text("Status Pembayaran", style = MaterialTheme.typography.bodyLarge)

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = insertUiEvent.status_pembayaran == "Lunas",
                onClick = {
                    onValueChange(insertUiEvent.copy(status_pembayaran = "Lunas"))
                },
                enabled = enabled
            )
            Text(
                text = "Lunas",
                modifier = Modifier.clickable(enabled = enabled) {
                    onValueChange(insertUiEvent.copy(status_pembayaran = "Lunas"))
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = insertUiEvent.status_pembayaran == "Belum Lunas",
                onClick = {
                    onValueChange(insertUiEvent.copy(status_pembayaran = "Belum Lunas"))
                },
                enabled = enabled
            )
            Text(
                text = "Belum Lunas",
                modifier = Modifier.clickable(enabled = enabled) {
                    onValueChange(insertUiEvent.copy(status_pembayaran = "Belum Lunas"))
                }
            )
        }

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenayanganDropdown(
    penayanganList: List<Penayangan>,
    selectedPenayanganId: Int?,
    onPenayanganSelected: (Int) -> Unit
) {
    val options = penayanganList.map { it.id_penayangan.toString() }

    val expanded = remember { mutableStateOf(false) }
    val currentSelection = remember {
        mutableStateOf(selectedPenayanganId?.toString() ?: options.getOrNull(0)) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            value = currentSelection.value ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Id Penayangan") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        currentSelection.value = selectionOption
                        expanded.value = false
                        onPenayanganSelected(selectionOption.toInt())
                    }
                )
            }
        }
    }
}