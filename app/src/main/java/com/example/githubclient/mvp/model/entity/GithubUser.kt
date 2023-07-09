package com.example.githubclient.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    @SerializedName("id")
    val idMy: String,
    val login: String,
    val avatarUrl: String? = null,
    val reposUrl: String? = null
) : Parcelable