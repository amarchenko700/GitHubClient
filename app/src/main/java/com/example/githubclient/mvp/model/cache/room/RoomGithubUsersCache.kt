package com.example.githubclient.mvp.model.cache.room

import com.example.githubclient.mvp.model.cache.IGithubUsersCache
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.entity.room.RoomGithubUser

class RoomGithubUsersCache : IGithubUsersCache {
    override fun saveUsersIntoCache(db: Database, githubUsers: List<GithubUser>) {
        val roomUsers = githubUsers.map { user ->
            RoomGithubUser(
                user.idMy,
                user.login,
                user.avatarUrl ?: "",
                user.reposUrl ?: ""
            )
        }
        db.userDao.insert(roomUsers)
    }

    override fun getGithubUsersFromCache(db: Database): List<GithubUser> =
        db.userDao.getAll().map { roomUser ->
            GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
        }
}