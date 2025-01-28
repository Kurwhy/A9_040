package com.example.lastproject_pam.ui.view.penayangan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastproject_pam.R
import com.example.lastproject_pam.model.Penayangan
import com.example.lastproject_pam.ui.costumwidget.CoustumeTopAppBar
import com.example.lastproject_pam.ui.navigation.DestinasiNavigasi
import com.example.lastproject_pam.ui.viewmodel.PenyediaViewModel
import com.example.lastproject_pam.ui.viewmodel.penayangan.HomePenayanganViewModel
import com.example.lastproject_pam.ui.viewmodel.penayangan.HomePnygnUiState

object DestinasiHomePenayangan : DestinasiNavigasi {
    override val route = "homePenayangan"
    override val titleRes = "Daftar Penayangan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPenayangan(
    navigateToltemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToHomeFilm: () -> Unit = {},
    navigateToHomeTiket: () -> Unit = {},
    navigateToHomeStudio: () -> Unit = {},
    navigateToHomePenayangan: () -> Unit = {},
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomePenayanganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiHomePenayangan.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPnygn()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToltemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Penayangan")
            }
        },
        bottomBar = {
            BottomNavigationBarPenayangan(
                navigateToHomeFilm = navigateToHomeFilm,
                navigateToHomeTiket = navigateToHomeTiket,
                navigateToHomeStudio = navigateToHomeStudio,
                navigateToHomePenayangan = navigateToHomePenayangan
            )
        }
    ) { innerPadding ->
        HomeStatusPenayangan(
            homeUiState = viewModel.penyanganUiState,
            retryAction = { viewModel.getPnygn() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePnygn(it.id_penayangan)
                viewModel.getPnygn()
            }
        )
    }
}


@Composable
fun BottomNavigationBarPenayangan(
    navigateToHomeFilm: () -> Unit,
    navigateToHomeTiket: () -> Unit,
    navigateToHomeStudio: () -> Unit,
    navigateToHomePenayangan: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 13.dp, horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationButton(
                    onClick = navigateToHomeFilm,
                    icon = painterResource(id = R.drawable.film),
                    contentDescription = "Home Film",
                    label = "Film",
                    isActive = false
                )

                NavigationButton(
                    onClick = navigateToHomePenayangan,
                    icon = painterResource(id = R.drawable.search),
                    contentDescription = "Home Penayangan",
                    label = "Penayangan",
                    isActive = true
                )

                NavigationButton(
                    onClick = navigateToHomeTiket,
                    icon = painterResource(id = R.drawable.ticket),
                    contentDescription = "Home Tiket",
                    label = "Tiket",
                    isActive = false
                )

                NavigationButton(
                    onClick = navigateToHomeStudio,
                    icon = painterResource(id = R.drawable.theater),
                    contentDescription = "Home Studio",
                    label = "Studio",
                    isActive = false
                )
            }
        }
    }
}

@Composable
fun NavigationButton(
    onClick: () -> Unit,
    icon: Painter,
    contentDescription: String,
    label: String,
    isActive: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FloatingActionButton(
            onClick = if (isActive) { {} } else { onClick },
            shape = CircleShape,
            modifier = Modifier
                .size(54.dp)
                .shadow(8.dp, CircleShape),
            containerColor = if (isActive) MaterialTheme.colorScheme
                .secondary else MaterialTheme.colorScheme.tertiary
        ) {
            Image(
                painter = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun HomeStatusPenayangan(
    homeUiState: HomePnygnUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Penayangan) -> Unit = {},
) {
    when (homeUiState) {
        is HomePnygnUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomePnygnUiState.Success ->
            if (homeUiState.penayangan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Penayangan")
                }
            } else {
                PenayanganLayout(
                    penayangan = homeUiState.penayangan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_penayangan)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomePnygnUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Image(
            painter = painterResource(id = R.drawable.connection_error),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = retryAction
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PenayanganLayout(
    penayangan: List<Penayangan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Penayangan) -> Unit,
    onDeleteClick: (Penayangan) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(penayangan) { penayangan ->
            PenayanganCard(
                penayangan = penayangan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDetailClick(penayangan)
                    },
                onDeleteClick = {
                    onDeleteClick(penayangan)
                }
            )
        }
    }
}

@Composable
fun PenayanganCard(
    penayangan: Penayangan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penayangan) -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.movie),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = penayangan.id_penayangan.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "TglPenayangan: ${penayangan.tanggal_penayangan}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Harga Tiket: ${penayangan.harga_tiket}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(onClick = { deleteConfirmationRequired = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(penayangan)
            },
            onDeleteCancel =  {
                deleteConfirmationRequired = false
            }, modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDeleteCancel,
        title = {
            Text(
                "Hapus Data",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Text(
                "Apakah Anda yakin ingin menghapus data ini? Tindakan ini tidak dapat dibatalkan.",
                fontSize = 16.sp
            )
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        confirmButton = {
            Button(
                onClick = onDeleteConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Hapus", color = Color.White)
            }
        }
    )
}