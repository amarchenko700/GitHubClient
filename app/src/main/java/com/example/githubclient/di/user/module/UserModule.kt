package com.example.githubclient.di.user.module

import com.example.githubclient.App
import com.example.githubclient.di.user.UserScope
import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.cache.IGithubUsersCache
import com.example.githubclient.mvp.model.cache.room.RoomGithubUsersCache
import com.example.githubclient.mvp.model.entity.network.INetworkStatus
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo
import dagger.Module
import dagger.Provides

@Module
class UserModule {
    @Provides
    fun usersCache(): IGithubUsersCache = RoomGithubUsersCache()

    @UserScope
    @Provides
    fun usersRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        db: Database,
        cache: IGithubUsersCache
    ): IGithubUsersRepo = RetrofitGithubUsersRepo(api, networkStatus, db, cache)

    @UserScope
    @Provides
    open fun scopeContainer(app: App): IUserScopeContainer = app
}