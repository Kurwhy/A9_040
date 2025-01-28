package com.example.lastproject_pam.ui.view.penayangan

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
import com.example.lastproject_pam.model.Penayangan
import com.example.lastproject_pam.ui.costumwidget.CoustumeTopAppBar
import com.example.lastproject_pam.ui.navigation.DestinasiNavigasi
import com.example.lastproject_pam.ui.viewmodel.PenyediaViewModel
import com.example.lastproject_pam.ui.viewmodel.penayangan.DetailPenayanganUiState
import com.example.lastproject_pam.ui.viewmodel.penayangan.DetailPenayanganViewModel

object DestinasiDetailPenayangan: DestinasiNavigasi {
    override val route = "detailPenayangan"
    override val titleRes = "Detail Data Penayangan"
    const val ID_PENAYANGAN = "id_penayangan"
    val routesWithArg = "$route/{$ID_PENAYANGAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenPenayangan(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPenayanganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailPenayangan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPenayanganbyId()
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
        DetailStatusPenayangan(
            modifier = Modifier.padding(innerPadding),
            detailPenayanganUiState = viewModel.penayanganDetailState,
            retryAction = { viewModel.getPenayanganbyId() }
        )
    }
}

@Composable
fun DetailStatusPenayangan(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailPenayanganUiState: DetailPenayanganUiState
) {
    when (detailPenayanganUiState) {
        is DetailPenayanganUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailPenayanganUiState.Success -> {
            if (detailPenayanganUiState.penayangan.id_penayangan == 0) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailPnygn(
                    penayangan = detailPenayanganUiState.penayangan, modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailPenayanganUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPnygn(
    modifier: Modifier = Modifier,
    penayangan: Penayangan
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
                text = "Penayangan Details",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            DetaillayoutPnygn(
                judul = "Id Tayang",
                isi = penayangan.id_penayangan.toString()
            )
            DetaillayoutPnygn(
                judul = "Id Film",
                isi = penayangan.id_film.toString()
            )
            DetaillayoutPnygn(
                judul = "Id Studio",
                isi = penayangan.id_studio.toString()
            )
            DetaillayoutPnygn(
                judul = "Harga Tiket",
                isi = penayangan.harga_tiket.toString()
            )
            DetaillayoutPnygn(
                judul = "Tgl Tayang ",
                isi = penayangan.tanggal_penayangan
            )
        }
    }
}

@Composable
fun DetaillayoutPnygn(
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