package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.view.UserRepoView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class UserRepoPresenter(private val router: Router, private val screens: IScreens) :
    MvpPresenter<UserRepoView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}