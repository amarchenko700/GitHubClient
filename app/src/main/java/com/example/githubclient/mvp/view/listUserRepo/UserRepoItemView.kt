package com.example.githubclient.mvp.view.listUserRepo

import com.example.githubclient.mvp.view.list.IItemView

interface UserRepoItemView : IItemView {
    fun setNameRepo(nameRepo: String)
}