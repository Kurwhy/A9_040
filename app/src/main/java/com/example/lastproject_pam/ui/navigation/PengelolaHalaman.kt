package com.example.lastproject_pam.ui.navigation

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lastproject_pam.ui.view.film.DestinasiDetailFilm
import com.example.lastproject_pam.ui.view.film.DestinasiEntryFilm
import com.example.lastproject_pam.ui.view.film.DestinasiHomeFilm
import com.example.lastproject_pam.ui.view.film.DestinasiUpdateFlm
import com.example.lastproject_pam.ui.view.film.DetailScreenFilm
import com.example.lastproject_pam.ui.view.film.EntryFlmScreen
import com.example.lastproject_pam.ui.view.film.HomeScreenFilm
import com.example.lastproject_pam.ui.view.film.UpdateScreenFilm
import com.example.lastproject_pam.ui.view.penayangan.DestinasiDetailPenayangan
import com.example.lastproject_pam.ui.view.penayangan.DestinasiEntryPenayangan
import com.example.lastproject_pam.ui.view.penayangan.DestinasiHomePenayangan
import com.example.lastproject_pam.ui.view.penayangan.DestinasiUpdatePnygn
import com.example.lastproject_pam.ui.view.penayangan.DetailScreenPenayangan
import com.example.lastproject_pam.ui.view.penayangan.EntryPnygnScreen
import com.example.lastproject_pam.ui.view.penayangan.HomeScreenPenayangan
import com.example.lastproject_pam.ui.view.penayangan.UpdateScreenPenayangan
import com.example.lastproject_pam.ui.view.studio.DestinasiDetailStudio
import com.example.lastproject_pam.ui.view.studio.DestinasiEntryStudio
import com.example.lastproject_pam.ui.view.studio.DestinasiHomeStudio
import com.example.lastproject_pam.ui.view.studio.DestinasiUpdateSdio
import com.example.lastproject_pam.ui.view.studio.DetailScreenStudio
import com.example.lastproject_pam.ui.view.studio.EntrySdioScreen
import com.example.lastproject_pam.ui.view.studio.HomeScreenStudio
import com.example.lastproject_pam.ui.view.studio.UpdateScreenStudio
import com.example.lastproject_pam.ui.view.tiket.DestinasiDetailTiket
import com.example.lastproject_pam.ui.view.tiket.DestinasiEntryTiket
import com.example.lastproject_pam.ui.view.tiket.DestinasiHomeTiket
import com.example.lastproject_pam.ui.view.tiket.DestinasiUpdateTkit
import com.example.lastproject_pam.ui.view.tiket.DetailScreenTiket
import com.example.lastproject_pam.ui.view.tiket.EntryTkitScreen
import com.example.lastproject_pam.ui.view.tiket.HomeScreenTiket
import com.example.lastproject_pam.ui.view.tiket.UpdateScreenTiket

@OptIn(UnstableApi::class) @Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeFilm.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHomeFilm.route) {
            HomeScreenFilm(
                navigateToltemEntry = { navController.navigate(DestinasiEntryFilm.route) },
                onDetailClick = { id_film ->
                    navController.navigate("${DestinasiDetailFilm.route}/$id_film")
                },
                navigateToHomeTiket = { navController.navigate("homeTiket") },
                navigateToHomeStudio = { navController.navigate("homeStudio") },
                navigateToHomePenayangan = { navController.navigate("homePenayangan") }
            )
        }
        composable(DestinasiEntryFilm.route){
            EntryFlmScreen(navigateBack = {
                navController.navigate(DestinasiHomeFilm.route){
                    popUpTo(DestinasiHomeFilm.route){
                        inclusive = true
                    }
                }
            }
            )
        }
        composable(DestinasiDetailFilm.routesWithArg, arguments = listOf(navArgument(DestinasiDetailFilm.ID_FILM) {
            type = NavType.IntType }
        )
        ){
            val id_film = it.arguments?.getInt(DestinasiDetailFilm.ID_FILM)
            id_film?.let { id_film ->
                DetailScreenFilm(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateFlm.route}/$id_film") },
                    navigateBack = { navController.navigate(DestinasiHomeFilm.route) {
                        popUpTo(DestinasiHomeFilm.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdateFlm.routesWithArg, arguments = listOf(navArgument(DestinasiDetailFilm.ID_FILM){
            type = NavType.IntType }
        )
        ){
            val id_film = it.arguments?.getInt(DestinasiUpdateFlm.ID_FILM)
            id_film?.let { id_film ->
                UpdateScreenFilm(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }



        composable("homeTiket") {
            HomeScreenTiket(
                navigateToltemEntry = { navController.navigate(DestinasiEntryTiket.route) },
                onDetailClick = { id_tiket ->
                    navController.navigate("${DestinasiDetailTiket.route}/$id_tiket")
                },
                navigateToHomeFilm = { navController.navigate("homeFilm") },
                navigateToHomeStudio = { navController.navigate("homeStudio") },
                navigateToHomePenayangan = { navController.navigate("homePenayangan") }

            )
        }
        composable(DestinasiEntryTiket.route){
            EntryTkitScreen(navigateBack = {
                navController.navigate(DestinasiHomeTiket.route){
                    popUpTo(DestinasiHomeTiket.route){
                        inclusive = true
                    }
                }
            }
            )
        }
        composable(DestinasiDetailTiket.routesWithArg, arguments = listOf(navArgument(DestinasiDetailTiket.ID_TIKET) {
            type = NavType.IntType }
        )
        ){
            val id_tiket = it.arguments?.getInt(DestinasiDetailTiket.ID_TIKET)
            id_tiket?.let { id_tiket ->
                DetailScreenTiket(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateTkit.route}/$id_tiket") },
                    navigateBack = { navController.navigate(DestinasiHomeTiket.route) {
                        popUpTo(DestinasiHomeTiket.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdateTkit.routesWithArg, arguments = listOf(navArgument(DestinasiDetailTiket.ID_TIKET){
            type = NavType.IntType }
        )
        ){
            val id_tiket = it.arguments?.getInt(DestinasiUpdateTkit.ID_TIKET)
            id_tiket?.let { id_tiket ->
                UpdateScreenTiket(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }



        composable("homeStudio") {
            HomeScreenStudio(
                navigateToltemEntry = { navController.navigate(DestinasiEntryStudio.route) },
                onDetailClick = { id_studio ->
                    navController.navigate("${DestinasiDetailStudio.route}/$id_studio")
                },
                navigateToHomeFilm = { navController.navigate("homeFilm") },
                navigateToHomePenayangan = { navController.navigate("homePenayangan") },
                navigateToHomeTiket = { navController.navigate("homeTiket") }
            )
        }
        composable(DestinasiEntryStudio.route){
            EntrySdioScreen(navigateBack = {
                navController.navigate(DestinasiHomeStudio.route){
                    popUpTo(DestinasiHomeStudio.route){
                        inclusive = true
                    }
                }
            }
            )
        }
        composable(DestinasiDetailStudio.routesWithArg, arguments = listOf(navArgument(DestinasiDetailStudio.ID_STUDIO) {
            type = NavType.IntType }
        )
        ){
            val id_studio = it.arguments?.getInt(DestinasiDetailStudio.ID_STUDIO)
            id_studio?.let { id_studio ->
                DetailScreenStudio(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateSdio.route}/$id_studio") },
                    navigateBack = { navController.navigate(DestinasiHomeStudio.route) {
                        popUpTo(DestinasiHomeStudio.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdateSdio.routesWithArg, arguments = listOf(navArgument(DestinasiDetailStudio.ID_STUDIO){
            type = NavType.IntType }
        )
        ){
            val id_studio = it.arguments?.getInt(DestinasiUpdateSdio.ID_STUDIO)
            id_studio?.let { id_studio ->
                UpdateScreenStudio(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }



        composable("homePenayangan") {
            HomeScreenPenayangan(
                navigateToltemEntry = { navController.navigate(DestinasiEntryPenayangan.route) },
                onDetailClick = { id_penayangan ->
                    navController.navigate("${DestinasiDetailPenayangan.route}/$id_penayangan")
                },
                navigateToHomeFilm = { navController.navigate("homeFilm") },
                navigateToHomeStudio = { navController.navigate("homeStudio") },
                navigateToHomeTiket = { navController.navigate("homeTiket") }
            )
        }
        composable(DestinasiEntryPenayangan.route){
            EntryPnygnScreen(navigateBack = {
                navController.navigate(DestinasiHomePenayangan.route){
                    popUpTo(DestinasiHomePenayangan.route){
                        inclusive = true
                    }
                }
            }
            )
        }
        composable(DestinasiDetailPenayangan.routesWithArg, arguments = listOf(navArgument(DestinasiDetailPenayangan.ID_PENAYANGAN) {
            type = NavType.IntType }
        )
        ){
            val id_penayangan = it.arguments?.getInt(DestinasiDetailPenayangan.ID_PENAYANGAN)
            id_penayangan?.let { id_penayangan ->
                DetailScreenPenayangan(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdatePnygn.route}/$id_penayangan") },
                    navigateBack = { navController.navigate(DestinasiHomePenayangan.route) {
                        popUpTo(DestinasiHomePenayangan.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdatePnygn.routesWithArg, arguments = listOf(navArgument(DestinasiDetailPenayangan.ID_PENAYANGAN){
            type = NavType.IntType }
        )
        ){
            val id_penayangan = it.arguments?.getInt(DestinasiUpdatePnygn.ID_PENAYANGAN)
            id_penayangan?.let { id_penayangan ->
                UpdateScreenPenayangan(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}