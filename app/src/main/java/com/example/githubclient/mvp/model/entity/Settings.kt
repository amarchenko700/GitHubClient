package com.example.githubclient.mvp.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Settings(
    val sizeList: Int
) : Parcelable