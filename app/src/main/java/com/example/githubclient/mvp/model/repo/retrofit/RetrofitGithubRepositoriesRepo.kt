package com.example.githubclient.mvp.model.repo.retrofit

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.model.entity.network.INetworkStatus
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.IGithubRepoCache
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RetrofitGithubRepositoriesRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus
) : IGithubRepositoriesRepo {

    @Inject
    lateinit var db: Database

    @Inject
    lateinit var cache: IGithubRepoCache

    override fun getRepositories(user: GithubUser) =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.reposUrl?.let { url ->
                    api.getUserRepo(user.login).flatMap { repositories ->
                        Single.fromCallable {
                            cache.saveGithubUsersRepoIntoCache(db, user, repositories)
                            repositories
                        }
                    }
                }
                    ?: Single.error<List<GithubUserRepository>>(RuntimeException("User has no repos url"))
                        .subscribeOn(
                            Schedulers.io()
                        )
            } else {
                Single.fromCallable {
                    cache.getGithubUsersRepo(db, user)
                }
            }
        }.subscribeOn(Schedulers.io())
}