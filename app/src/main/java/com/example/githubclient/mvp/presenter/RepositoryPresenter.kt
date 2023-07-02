package com.example.githubclient.mvp.presenter

import com.example.githubclient.navigation.IScreens
import com.example.githubclient.ui.fragment.view.UserRepoView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class RepositoryPresenter() :
    MvpPresenter<UserRepoView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}