package com.example.githubclient.navigation

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.ui.fragment.SettingsFragment
import com.example.githubclient.ui.fragment.UserFragment
import com.example.githubclient.ui.fragment.UserRepoFragment
import com.example.githubclient.ui.fragment.UsersFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun githubUser(githubUser: GithubUser) =
        FragmentScreen { UserFragment.newInstance(githubUser) }

    override fun githubUserRepo(githubUserRepo: GithubUserRepository) =
        FragmentScreen { UserRepoFragment.newInstance(githubUserRepo) }

    override fun settings(): Screen =
        FragmentScreen { SettingsFragment.newInstance() }
}