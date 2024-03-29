package com.example.githubclient

import android.app.Application
import com.example.githubclient.di.AppComponent
import com.example.githubclient.di.DaggerAppComponent
import com.example.githubclient.di.module.AppModule
import com.example.githubclient.di.repository.RepositorySubcomponent
import com.example.githubclient.di.repository.module.IRepositoryScopeContainer
import com.example.githubclient.di.user.UserSubcomponent
import com.example.githubclient.di.user.module.IUserScopeContainer

class App : Application(), IUserScopeContainer, IRepositoryScopeContainer {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
        private set

    var userSubcomponent: UserSubcomponent? = null
        private set

    var repositorySubcomponent: RepositorySubcomponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun initUserSubcomponent() = appComponent.userSubcomponent().also {
        userSubcomponent = it
    }

    fun initRepositorySubcomponent() = userSubcomponent?.repositorySubcomponent().also {
        repositorySubcomponent = it
    }

    override fun releaseRepositoryScope() {
        repositorySubcomponent = null
    }

    override fun releaseUserScope() {
        userSubcomponent = null
    }
}