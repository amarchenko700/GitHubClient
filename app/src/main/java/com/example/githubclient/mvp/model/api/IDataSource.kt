package com.example.githubclient.mvp.model.api

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IDataSource {
    @GET("/users")
    fun getUsers(@Query("per_page") perPage: Int): Single<List<GithubUser>>

    @GET("/users/{username}/repos")
    fun getUserRepo(
        @Path("username") username: String,
        @Query("per_page") perPage: Int
    ): Single<List<GithubUserRepository>>
}