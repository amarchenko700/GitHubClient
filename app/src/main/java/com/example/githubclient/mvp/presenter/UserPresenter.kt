package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.view.UserView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class UserPresenter(private val router: Router, val screens: IScreens) : MvpPresenter<UserView>() {

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
