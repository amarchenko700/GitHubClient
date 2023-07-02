package com.example.githubclient.di.repository.module

import com.example.githubclient.App
import com.example.githubclient.di.repository.RepositoryScope
import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.cache.IGithubUsersCache
import com.example.githubclient.mvp.model.cache.room.IGithubRepositoriesCache
import com.example.githubclient.mvp.model.cache.room.RoomGithubRepositoriesCache
import com.example.githubclient.mvp.model.entity.network.INetworkStatus
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun repositoriesCache(): IGithubRepositoriesCache = RoomGithubRepositoriesCache()

    @RepositoryScope
    @Provides
    fun repositoriesRepo(
        api: IDataSource, networkStatus: INetworkStatus, db: Database,
        cache: IGithubRepositoriesCache
    )
            : IGithubRepositoriesRepo =
        RetrofitGithubRepositoriesRepo(api, networkStatus, db, cache)

    @RepositoryScope
    @Provides
    open fun scopeContainer(app: App): IRepositoryScopeContainer = app

}