package com.example.lastproject_pam.ui.view.studio

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastproject_pam.ui.costumwidget.CoustumeTopAppBar
import com.example.lastproject_pam.ui.navigation.DestinasiNavigasi
import com.example.lastproject_pam.ui.viewmodel.PenyediaViewModel
import com.example.lastproject_pam.ui.viewmodel.studio.UpdateStudioViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateSdio: DestinasiNavigasi {
    override val route = "update_studio"
    override val titleRes = "Update Studio"
    const val ID_STUDIO = "id_studio"
    val routesWithArg = "$route/{$ID_STUDIO}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenStudio(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateStudioViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiUpdateSdio.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            insertSdioUiState = viewModel.updateUiState,
            onStudioValueChange = viewModel::updateInsertSdioState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateSdio()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}