package com.example.githubclient.di.module

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.entity.network.INetworkStatus
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.IGithubRepoCache
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun usersRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        db: Database,
        cache: IGithubRepoCache
    )
            : IGithubUsersRepo = RetrofitGithubUsersRepo(api, networkStatus, db, cache)

    @Singleton
    @Provides
    fun repositoriesRepo(
        api: IDataSource, networkStatus: INetworkStatus, db: Database,
        cache: IGithubRepoCache
    )
            : IGithubRepositoriesRepo =
        RetrofitGithubRepositoriesRepo(api, networkStatus, db, cache)
}