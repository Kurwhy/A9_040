package com.example.lastproject_pam.ui.view.film

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
import com.example.lastproject_pam.model.Film
import com.example.lastproject_pam.ui.costumwidget.CoustumeTopAppBar
import com.example.lastproject_pam.ui.navigation.DestinasiNavigasi
import com.example.lastproject_pam.ui.viewmodel.PenyediaViewModel
import com.example.lastproject_pam.ui.viewmodel.film.DetailFilmUiState
import com.example.lastproject_pam.ui.viewmodel.film.DetailFilmViewModel

object DestinasiDetailFilm: DestinasiNavigasi {
    override val route = "detailFilm"
    override val titleRes = "Detail Data Film"
    const val ID_FILM = "id_film"
    val routesWithArg = "$route/{$ID_FILM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenFilm(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailFilmViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailFilm.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getFilmbyId()
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
        DetailStatusFlm(
            modifier = Modifier.padding(innerPadding),
            detailFilmUiState = viewModel.filmDetailState,
            retryAction = { viewModel.getFilmbyId() }
        )
    }
}

@Composable
fun DetailStatusFlm(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailFilmUiState: DetailFilmUiState
) {
    when (detailFilmUiState) {
        is DetailFilmUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailFilmUiState.Success -> {
            if (detailFilmUiState.film.id_film == 0) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailFlm(
                    film = detailFilmUiState.film, modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailFilmUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailFlm(
    modifier: Modifier = Modifier,
    film: Film
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
                text = "Film Details",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            DetailFilmlayout(
                judul = "Id Film",
                isi = film.id_film.toString()
            )
            DetailFilmlayout(
                judul = "Judul Film",
                isi = film.judul_film
            )
            DetailFilmlayout(
                judul = "Genre",
                isi = film.genre
            )
            DetailFilmlayout(
                judul = "Durasi",
                isi = film.durasi
            )
            DetailFilmlayout(
                judul = "Rating Usia",
                isi = film.rating_usia
            )
            DetailFilmlayout(
                judul = "Deskripsi",
                isi = film.deskripsi
            )
        }
    }
}

@Composable
fun DetailFilmlayout(
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