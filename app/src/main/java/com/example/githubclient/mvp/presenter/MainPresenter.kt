package com.example.githubclient.mvp.presenter

import com.example.githubclient.navigation.IScreens
import com.example.githubclient.ui.fragment.view.MainView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MainPresenter(private val router: Router, private val screens: IScreens) :
    MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.navigateTo(screens.users())
    }

    fun backClicked() {
        router.finishChain()
    }

}