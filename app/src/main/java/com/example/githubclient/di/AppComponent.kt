package com.example.githubclient.di

import com.example.githubclient.di.module.ApiModule
import com.example.githubclient.di.module.AppModule
import com.example.githubclient.di.module.CacheModule
import com.example.githubclient.di.module.CiceroneModule
import com.example.githubclient.di.module.ImageLoaderModule
import com.example.githubclient.di.module.RepoModule
import com.example.githubclient.mvp.presenter.MainPresenter
import com.example.githubclient.mvp.presenter.UserPresenter
import com.example.githubclient.mvp.presenter.UsersPresenter
import com.example.githubclient.ui.activity.MainActivity
import com.example.githubclient.ui.adapter.UsersRVAdapter
import com.example.githubclient.ui.fragment.UserFragment
import com.example.githubclient.ui.fragment.UserRepoFragment
import com.example.githubclient.ui.fragment.UsersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        CacheModule::class,
        CiceroneModule::class,
        RepoModule::class,
        ImageLoaderModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(usersRVAdapter: UsersRVAdapter)

    // убрать эти инъекции
    fun inject(usersFragment: UsersFragment)
    fun inject(userFragment: UserFragment)
    fun inject(repositoryFragment: UserRepoFragment)
}