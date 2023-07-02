package com.example.githubclient.mvp.model.cache

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.room.Database

interface IGithubUsersCache {
    fun saveUsersIntoCache(db: Database, githubUsers: List<GithubUser>)
    fun getGithubUsersFromCache(db: Database): List<GithubUser>
}