package com.example.lastproject_pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lastproject_pam.BioskopApplication
import com.example.lastproject_pam.ui.viewmodel.film.DetailFilmViewModel
import com.example.lastproject_pam.ui.viewmodel.film.HomeFilmViewModel
import com.example.lastproject_pam.ui.viewmodel.film.InsertFilmViewModel
import com.example.lastproject_pam.ui.viewmodel.film.UpdateFilmViewModel
import com.example.lastproject_pam.ui.viewmodel.penayangan.DetailPenayanganViewModel
import com.example.lastproject_pam.ui.viewmodel.penayangan.HomePenayanganViewModel
import com.example.lastproject_pam.ui.viewmodel.penayangan.InsertPenayanganViewModel
import com.example.lastproject_pam.ui.viewmodel.penayangan.UpdatePenayanganViewModel
import com.example.lastproject_pam.ui.viewmodel.studio.DetailStudioViewModel
import com.example.lastproject_pam.ui.viewmodel.studio.HomeStudioViewModel
import com.example.lastproject_pam.ui.viewmodel.studio.InsertStudioViewModel
import com.example.lastproject_pam.ui.viewmodel.studio.UpdateStudioViewModel
import com.example.lastproject_pam.ui.viewmodel.tiket.DetailTiketViewModel
import com.example.lastproject_pam.ui.viewmodel.tiket.HomeTiketViewModel
import com.example.lastproject_pam.ui.viewmodel.tiket.InsertTiketViewModel
import com.example.lastproject_pam.ui.viewmodel.tiket.UpdateTiketViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeFilmViewModel(
            aplikasiBioskop().container.filmRepository) }

        initializer { HomePenayanganViewModel(
            aplikasiBioskop().container.penayanganRepository) }

        initializer { HomeStudioViewModel(
            aplikasiBioskop().container.studioRepository) }

        initializer { HomeTiketViewModel(
            aplikasiBioskop().container.tiketRepository) }



        initializer { InsertFilmViewModel(
            aplikasiBioskop().container.filmRepository) }

        initializer { InsertPenayanganViewModel(
            aplikasiBioskop().container.penayanganRepository,
            aplikasiBioskop().container.studioRepository,
            aplikasiBioskop().container.filmRepository) }

        initializer { InsertStudioViewModel(
            aplikasiBioskop().container.studioRepository) }

        initializer { InsertTiketViewModel(
            aplikasiBioskop().container.tiketRepository,
            aplikasiBioskop().container.penayanganRepository) }



        initializer { DetailFilmViewModel(createSavedStateHandle(),
            aplikasiBioskop().container.filmRepository) }

        initializer { DetailPenayanganViewModel(createSavedStateHandle(),
            aplikasiBioskop().container.penayanganRepository) }

        initializer { DetailStudioViewModel(createSavedStateHandle(),
            aplikasiBioskop().container.studioRepository) }

        initializer { DetailTiketViewModel(createSavedStateHandle(),
            aplikasiBioskop().container.tiketRepository) }

        initializer { UpdateFilmViewModel(createSavedStateHandle(),
            aplikasiBioskop().container.filmRepository) }

        initializer { UpdatePenayanganViewModel(createSavedStateHandle(),
            aplikasiBioskop().container.penayanganRepository,
            aplikasiBioskop().container.studioRepository,
            aplikasiBioskop().container.filmRepository) }

        initializer { UpdateStudioViewModel(createSavedStateHandle(),
            aplikasiBioskop().container.studioRepository) }

        initializer { UpdateTiketViewModel(createSavedStateHandle(),
            aplikasiBioskop().container.tiketRepository,
            aplikasiBioskop().container.penayanganRepository) }

    }
}

fun CreationExtras.aplikasiBioskop(): BioskopApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BioskopApplication)