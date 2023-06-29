package com.example.githubclient.ui.fragment.view.listUserRepo

import com.example.githubclient.ui.fragment.view.list.IItemView

interface UserRepoItemView : IItemView {
    fun setNameRepo(nameRepo: String)
}