package com.example.githubclient.di.module

import androidx.room.Room
import com.example.githubclient.App
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.GithubRepoCache
import com.example.githubclient.mvp.model.repo.IGithubRepoCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {
    @Singleton
    @Provides
    fun database(app: App): Database =
        Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
            .build()

    @Singleton
    @Provides
    fun usersCache(): IGithubRepoCache = GithubRepoCache()


}