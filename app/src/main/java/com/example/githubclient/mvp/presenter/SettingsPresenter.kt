package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.entity.room.RoomSettings
import com.example.githubclient.ui.fragment.view.SettingsView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class SettingsPresenter : MvpPresenter<SettingsView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var db: Database

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun saveSettings(sizeList: Int, sizeRepoList: Int) {
        db.settingsDao.saveSettings(RoomSettings(sizeList, sizeRepoList))
    }

    fun getSizeUsersList(): Int =
        db.settingsDao.getSettings()?.sizeList ?: 30

    fun getSizeUsersRepoList(): Int =
        db.settingsDao.getSettings()?.sizeRepoList ?: 30

}