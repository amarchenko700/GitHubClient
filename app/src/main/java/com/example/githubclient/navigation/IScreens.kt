package com.example.githubclient.navigation

import com.example.githubclient.mvp.model.GithubUser
import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun users(): Screen
    fun githubUser(githubUser: GithubUser): Screen
}