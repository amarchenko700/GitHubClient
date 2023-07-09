package com.example.githubclient.mvp.model.repo

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.entity.room.RoomGithubRepository
import com.example.githubclient.mvp.model.entity.room.RoomGithubUser

class GithubRepoCache : IGithubRepoCache {

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

    override fun saveGithubUsersRepoIntoCache(
        db: Database,
        githubUser: GithubUser,
        githubUserRepo: List<GithubUserRepository>
    ) {
        val roomUser = db.userDao.findByLogin(githubUser.login)
            ?: throw RuntimeException("No such user in cache")

        val roomRepos = githubUserRepo.map {
            RoomGithubRepository(
                it.id,
                it.name,
                it.forksCount,
                roomUser.id
            )
        }

        db.repositoryDao.insert(roomRepos)
    }

    override fun getGithubUsersRepo(
        db: Database,
        githubUser: GithubUser
    ): List<GithubUserRepository> {
        val roomUser = db.userDao.findByLogin(githubUser.login)
            ?: throw RuntimeException("No such user in cache")

        return db.repositoryDao.findForUser(roomUser.id).map {
            GithubUserRepository(it.id, it.name, it.forksCount)
        }
    }
}