package com.example.githubclient.mvp.model.repo

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import io.reactivex.rxjava3.core.Single

interface IGithubRepositoriesRepo {
    fun getRepositories(user: GithubUser): Single<List<GithubUserRepository>>
}