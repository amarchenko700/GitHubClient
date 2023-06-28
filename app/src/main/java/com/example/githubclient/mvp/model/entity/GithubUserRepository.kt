package com.example.githubclient.mvp.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUserRepository(
    val id: Int,
    val name: String,
    val forks_count: Int
) : Parcelable