package com.example.githubclient.di.repository

import com.example.githubclient.di.repository.module.RepositoryModule
import com.example.githubclient.mvp.presenter.RepositoryPresenter
import com.example.githubclient.mvp.presenter.UserPresenter
import dagger.Subcomponent

@RepositoryScope
@Subcomponent(
    modules = [
        RepositoryModule::class
    ]
)
interface RepositorySubcomponent {
    fun inject(userPresenter: UserPresenter)
    fun inject(repositoryPresenter: RepositoryPresenter)
}