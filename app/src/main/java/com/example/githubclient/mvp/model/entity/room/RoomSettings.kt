package com.example.githubclient.mvp.model.entity.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomSettings(
    var sizeList: Int,
    var sizeRepoList: Int,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0
)