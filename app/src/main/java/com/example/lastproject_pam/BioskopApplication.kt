package com.example.lastproject_pam

import android.app.Application
import com.example.lastproject_pam.di.AppContainer
import com.example.lastproject_pam.di.AppContainerImpl

class BioskopApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl()
    }
}