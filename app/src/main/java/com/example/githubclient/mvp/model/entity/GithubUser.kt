package com.example.githubclient.mvp.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    val id: Int,
    val login: String,
    val avatarUrl: String? = null,
    val reposUrl: String? = null
) : Parcelable