package com.example.lastproject_pam.ui.view.tiket

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
import com.example.lastproject_pam.model.Tiket
import com.example.lastproject_pam.ui.costumwidget.CoustumeTopAppBar
import com.example.lastproject_pam.ui.navigation.DestinasiNavigasi
import com.example.lastproject_pam.ui.viewmodel.PenyediaViewModel
import com.example.lastproject_pam.ui.viewmodel.tiket.HomeTiketViewModel
import com.example.lastproject_pam.ui.viewmodel.tiket.HomeTkitUiState

object DestinasiHomeTiket : DestinasiNavigasi {
    override val route = "homeTiket"
    override val titleRes = "Daftar Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTiket(
    navigateToltemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToHomeFilm: () -> Unit = {},
    navigateToHomeTiket: () -> Unit = {},
    navigateToHomePenayangan: () -> Unit = {},
    navigateToHomeStudio: () -> Unit = {},
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiHomeTiket.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTkit()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToltemEntry,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .padding(18.dp)
                    .size(50.dp)
                    .shadow(elevation = 6.dp, shape = MaterialTheme.shapes.medium),
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Tiket",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        bottomBar = {
            BottomNavigationBarTiket(
                navigateToHomeFilm = navigateToHomeFilm,
                navigateToHomeTiket = navigateToHomeTiket,
                navigateToHomePenayangan = navigateToHomePenayangan,
                navigateToHomeStudio = navigateToHomeStudio
            )
        }
    ){ innerPadding ->
        HomeStatusTiket(
            homeUiState = viewModel.tiketUiState,
            retryAction = { viewModel.getTkit() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTkit(it.id_tiket)
                viewModel.getTkit()
            }
        )
    }
}

@Composable
fun BottomNavigationBarTiket(
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
                    isActive = false
                )

                NavigationButton(
                    onClick = navigateToHomeTiket,
                    icon = painterResource(id = R.drawable.ticket),
                    contentDescription = "Home Tiket",
                    label = "Tiket",
                    isActive = true
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
fun HomeStatusTiket(
    homeUiState: HomeTkitUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Tiket) -> Unit = {},
){
    when (homeUiState) {
        is HomeTkitUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTkitUiState.Success ->
            if (homeUiState.tiket.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Tiket")
                }
            } else {
                TiketLayout(
                    tiket = homeUiState.tiket,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_tiket)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeTkitUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun TiketLayout(
    tiket: List<Tiket>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tiket) -> Unit,
    onDeleteClick: (Tiket) -> Unit = {},
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tiket){tiket ->
            TiketCard(
                tiket = tiket,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDetailClick(tiket)},
                onDeleteClick = {
                    onDeleteClick(tiket)
                }
            )
        }
    }
}

@Composable
fun TiketCard(
    tiket: Tiket,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tiket) -> Unit = {}
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
                painter = painterResource(R.drawable.ticket),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = tiket.id_tiket.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Jumlah Tiket: ${tiket.jumlah_tiket}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Total Harga: ${tiket.total_harga}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Status: ${tiket.status_pembayaran}",
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
                onDeleteClick(tiket)
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