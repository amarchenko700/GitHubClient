package com.example.githubclient.mvp.model.cache.room

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.model.entity.room.Database

interface IGithubRepositoriesCache {
    fun saveGithubUsersRepoIntoCache(
        db: Database,
        githubUser: GithubUser,
        githubUserRepo: List<GithubUserRepository>
    )

    fun getGithubUsersRepo(db: Database, githubUser: GithubUser): List<GithubUserRepository>
}