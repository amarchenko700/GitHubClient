package com.example.githubclient.mvp.model.repo.retrofit

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.cache.room.IGithubRepositoriesCache
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.model.entity.network.INetworkStatus
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: Database,
    private val cache: IGithubRepositoriesCache
) : IGithubRepositoriesRepo {

    override fun getRepositories(user: GithubUser) =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                val perPage = db.settingsDao.getSettings()?.sizeRepoList ?: 30
                user.reposUrl?.let { url ->
                    api.getUserRepo(user.login, perPage).flatMap { repositories ->
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