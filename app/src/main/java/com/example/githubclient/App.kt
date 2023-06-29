package com.example.githubclient

import android.app.Application
import com.example.githubclient.mvp.model.entity.network.INetworkStatus
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.GithubRepoCache
import com.example.githubclient.navigation.AndroidScreens
import com.gb.poplib.githubclient.ui.network.AndroidNetworkStatus
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class App : Application() {
    companion object {
        lateinit var githubRepoCache: GithubRepoCache
        lateinit var instance: App
        lateinit var networkStatus: INetworkStatus
    }

    private val cicerone: Cicerone<Router> by lazy { Cicerone.create() }
    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router
    val androidScreens = AndroidScreens()

    override fun onCreate() {
        super.onCreate()
        instance = this
        networkStatus = AndroidNetworkStatus(instance)
        githubRepoCache = GithubRepoCache()

        Database.create(this)
    }
}