package com.example.githubclient.mvp.model.repo.retrofit

import com.example.githubclient.App
import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.entity.network.INetworkStatus
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.IGithubRepoCache
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RetrofitGithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus
) : IGithubUsersRepo {

    @Inject
    lateinit var db: Database

    @Inject
    lateinit var cache: IGithubRepoCache

    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers().flatMap { users ->
                Single.fromCallable {
                    cache.saveUsersIntoCache(db, users)
                    users
                }
            }
        } else {
            Single.fromCallable {
                cache.getGithubUsersFromCache(db)
            }
        }
    }.subscribeOn(Schedulers.io())

    //override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
}