package com.example.githubclient.mvp.model.repo.retrofit

import com.example.githubclient.mvp.model.api.IDataSource
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo
import com.example.githubclient.mvp.model.entity.network.INetworkStatus
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.entity.room.RoomGithubUser
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: Database
) : IGithubUsersRepo {

    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline->
        if (isOnline) {
            api.getUsers().flatMap { users ->
                Single.fromCallable {
                    val roomUsers = users.map { user->
                        RoomGithubUser(user.id ,
                            user.login ,
                            user.avatarUrl ?: "",
                            user.reposUrl ?: "")
                    }
                    db.userDao.insert(roomUsers)
                    users
                }
            }
        } else {
            Single.fromCallable {
                db.userDao.getAll().map {roomUser ->
                    GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
                }
            }
        }
    }.subscribeOn(Schedulers.io())

    //override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
    override fun getUserRepo(userName: String) =
        api.getUserRepo(userName).subscribeOn(Schedulers.io())
}