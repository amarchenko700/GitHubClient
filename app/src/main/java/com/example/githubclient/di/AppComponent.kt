package com.example.githubclient.di

import com.example.githubclient.di.module.ApiModule
import com.example.githubclient.di.module.AppModule
import com.example.githubclient.di.module.CacheModule
import com.example.githubclient.di.module.CiceroneModule
import com.example.githubclient.di.module.ImageLoaderModule
import com.example.githubclient.di.user.UserSubcomponent
import com.example.githubclient.mvp.presenter.MainPresenter
import com.example.githubclient.mvp.presenter.SettingsPresenter
import com.example.githubclient.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        CacheModule::class,
        CiceroneModule::class,
        ImageLoaderModule::class
    ]
)
interface AppComponent {
    fun userSubcomponent(): UserSubcomponent

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(settingsPresenter: SettingsPresenter)
}