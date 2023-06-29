package com.example.githubclient.mvp.model.repo.retrofit

import com.example.githubclient.App
import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.entity.network.INetworkStatus
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: Database
) : IGithubUsersRepo {

    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers().flatMap { users ->
                Single.fromCallable {
                    App.githubRepoCache.saveUsersIntoCache(db, users)
                    users
                }
            }
        } else {
            Single.fromCallable {
                App.githubRepoCache.getGithubUsersFromCache(db)
            }
        }
    }.subscribeOn(Schedulers.io())

    //override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
}