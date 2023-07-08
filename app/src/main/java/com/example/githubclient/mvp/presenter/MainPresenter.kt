package com.example.githubclient.mvp.presenter

import com.example.githubclient.R
import com.example.githubclient.navigation.IScreens
import com.example.githubclient.ui.activity.IMainPresenter
import com.example.githubclient.ui.fragment.view.MainView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter :
    MvpPresenter<MainView>(), IMainPresenter {

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

    override fun onBottomNavigationItemClick(itemId: Int): Boolean {
        return when (itemId) {
            R.id.usersList -> {
                router.navigateTo(screens.users())
                true
            }

            R.id.settings -> {
                router.navigateTo(screens.settings())
                true
            }

            else -> {
                false
            }
        }
    }

}