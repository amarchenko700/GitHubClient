package com.example.githubclient.mvp.model.cache.room

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.entity.room.RoomGithubRepository
import com.example.githubclient.mvp.model.entity.room.RoomGithubUser

class RoomGithubRepositoriesCache : IGithubRepositoriesCache {

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