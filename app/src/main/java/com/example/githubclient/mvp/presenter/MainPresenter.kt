package com.example.githubclient.mvp.presenter

import com.example.githubclient.navigation.IScreens
import com.example.githubclient.ui.fragment.view.MainView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter :
    MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.navigateTo(screens.users())
    }

    fun backClicked() {
        router.finishChain()
    }

}