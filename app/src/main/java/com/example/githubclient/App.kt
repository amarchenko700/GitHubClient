package com.example.githubclient

import android.app.Application
import com.example.githubclient.di.AppComponent
import com.example.githubclient.di.DaggerAppComponent
import com.example.githubclient.di.module.AppModule
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.GithubRepoCache
import com.example.githubclient.navigation.AndroidScreens
import com.gb.poplib.githubclient.ui.network.AndroidNetworkStatus
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}