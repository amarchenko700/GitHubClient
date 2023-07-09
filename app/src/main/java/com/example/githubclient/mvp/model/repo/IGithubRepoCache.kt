package com.example.githubclient.mvp.model.repo

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.model.entity.room.Database

interface IGithubRepoCache {
    fun saveUsersIntoCache(db: Database, githubUsers: List<GithubUser>)
    fun getGithubUsersFromCache(db: Database): List<GithubUser>

    fun saveGithubUsersRepoIntoCache(
        db: Database,
        githubUser: GithubUser,
        githubUserRepo: List<GithubUserRepository>
    )

    fun getGithubUsersRepo(db: Database, githubUser: GithubUser): List<GithubUserRepository>
}