package com.example.lastproject_pam.ui.view.penayangan

import com.example.lastproject_pam.ui.costumwidget.CoustumeTopAppBar
import com.example.lastproject_pam.ui.navigation.DestinasiNavigasi
import com.example.lastproject_pam.ui.viewmodel.PenyediaViewModel
import com.example.lastproject_pam.ui.viewmodel.penayangan.InsertPenayanganViewModel
import com.example.lastproject_pam.ui.viewmodel.penayangan.InsertPnygnUiState
import com.example.lastproject_pam.ui.viewmodel.penayangan.InsertUiEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastproject_pam.model.Film
import com.example.lastproject_pam.model.Studio
import kotlinx.coroutines.launch

object DestinasiEntryPenayangan : DestinasiNavigasi {
    override val route = "item_entry_penayangan"
    override val titleRes = "Entry Penayangan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPnygnScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenayanganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiEntryPenayangan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertPnygnUiState = viewModel.uiState,
            studioList = viewModel.studioList,
            filmList = viewModel.filmList,
            onPenayanganValueChange = viewModel::updateInsertPnygnState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPnygn()
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
    insertPnygnUiState: InsertPnygnUiState,
    studioList: List<Studio>,
    filmList: List<Film>,
    onPenayanganValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertPnygnUiState.insertUiEvent,
            onValueChange = onPenayanganValueChange,
            studioList = studioList,
            filmList = filmList,
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
    studioList: List<Studio>,
    filmList: List<Film>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.id_penayangan.toString(),
            onValueChange = {
                val intValue = it.toIntOrNull()
                if (intValue != null) {
                    onValueChange(insertUiEvent.copy(id_penayangan = intValue))
                }
            },
            label = { Text("Id Penayangan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        FilmDropdown(
            filmList = filmList,
            selectedFilmId = insertUiEvent.id_film,
            onFilmSelected = { selectedStudioId ->
                onValueChange(insertUiEvent.copy(id_film = selectedStudioId))
            }
        )

        StudioDropdown(
            studioList = studioList,
            selectedStudioId = insertUiEvent.id_studio,
            onStudioSelected = { selectedStudioId ->
                onValueChange(insertUiEvent.copy(id_studio = selectedStudioId))
            }
        )

        OutlinedTextField(
            value = insertUiEvent.harga_tiket.toString(),
            onValueChange = {
                val intValue = it.toIntOrNull()
                if (intValue != null) {
                    onValueChange(insertUiEvent.copy(harga_tiket = intValue))
                }
            },
            label = { Text("Harga Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.tanggal_penayangan,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggal_penayangan = it)) },
            label = { Text("Tanggal Penayangan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

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
fun StudioDropdown(
    studioList: List<Studio>,
    selectedStudioId: Int?,
    onStudioSelected: (Int) -> Unit
) {
    val StudioMap = studioList.associate { it.nama_studio to it.id_studio }
    val options = StudioMap.keys.toList()

    val expanded = remember { mutableStateOf(false) }
    val currentSelection = remember {
        mutableStateOf(
            StudioMap.entries.find { it.value == selectedStudioId }?.key ?: options.getOrNull(0)
        )
    }
    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            value = currentSelection.value ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Nama Studio") },
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
                        onStudioSelected(StudioMap[selectionOption]!!)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDropdown(
    filmList: List<Film>,
    selectedFilmId: Int?,
    onFilmSelected: (Int) -> Unit
) {
    val filmMap = filmList.associate { it.judul_film to it.id_film }
    val options = filmMap.keys.toList()

    val expanded = remember { mutableStateOf(false) }
    val currentSelection = remember {
        mutableStateOf(filmMap.entries.find { it.value == selectedFilmId }?.key ?: options.getOrNull(0))
    }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            value = currentSelection.value ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Judul Film") },
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
                        onFilmSelected(filmMap[selectionOption]!!)
                    }
                )
            }
        }
    }
}