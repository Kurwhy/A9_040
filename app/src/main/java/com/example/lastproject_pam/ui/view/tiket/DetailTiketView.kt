package com.example.lastproject_pam.ui.view.tiket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastproject_pam.model.Tiket
import com.example.lastproject_pam.ui.costumwidget.CoustumeTopAppBar
import com.example.lastproject_pam.ui.navigation.DestinasiNavigasi
import com.example.lastproject_pam.ui.viewmodel.PenyediaViewModel
import com.example.lastproject_pam.ui.viewmodel.tiket.DetailTiketUiState
import com.example.lastproject_pam.ui.viewmodel.tiket.DetailTiketViewModel

object DestinasiDetailTiket: DestinasiNavigasi {
    override val route = "detailTiket"
    override val titleRes = "Detail Data Tiket"
    const val ID_TIKET = "id_tiket"
    val routesWithArg = "$route/{$ID_TIKET}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTiket(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailTiket.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTiketbyId()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    ) { innerPadding ->
        DetailStatusTkit(
            modifier = Modifier.padding(innerPadding),
            detailTiketUiState = viewModel.tiketDetailState,
            retryAction = { viewModel.getTiketbyId() }
        )
    }
}

@Composable
fun DetailStatusTkit(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailTiketUiState: DetailTiketUiState
) {
    when (detailTiketUiState) {
        is DetailTiketUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailTiketUiState.Success -> {
            if (detailTiketUiState.tiket.id_tiket == 0) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailTkit(
                    tiket = detailTiketUiState.tiket, modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailTiketUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailTkit(
    modifier: Modifier = Modifier,
    tiket: Tiket
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Tiket Details",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            DetailTiketlayout(
                judul = "Id Film",
                isi = tiket.id_tiket.toString()
            )
            DetailTiketlayout(
                judul = "Judul Film",
                isi = tiket.id_penayangan.toString()
            )
            DetailTiketlayout(
                judul = "Genre",
                isi = tiket.jumlah_tiket.toString()
            )
            DetailTiketlayout(
                judul = "Durasi",
                isi = tiket.total_harga.toString()
            )
            DetailTiketlayout(
                judul = "Rating Usia",
                isi = tiket.status_pembayaran
            )
        }
    }
}

@Composable
fun DetailTiketlayout(
    judul: String,
    isi: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = judul,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = ":",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = isi,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(2f)
        )
    }
}