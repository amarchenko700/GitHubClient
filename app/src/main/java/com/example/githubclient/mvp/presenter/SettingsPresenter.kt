package com.example.githubclient.mvp.presenter

import com.example.githubclient.navigation.IScreens
import com.example.githubclient.ui.fragment.view.SettingsView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class SettingsPresenter : MvpPresenter<SettingsView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

//    @Inject
//    lateinit var settingsRepository: IRepositorySettingsContainer

    fun backPressed(): Boolean {
        return true
    }

//    override val callbackSaveSettings = {
//
//    }

    override fun onDestroy() {
        //settingsRepository.releaseSettingsScope()
        super.onDestroy()
    }
}