package com.example.githubclient.mvp.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUserRepository(
    val id: String,
    val name: String,
    val forksCount: Int
) : Parcelable