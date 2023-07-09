package com.example.githubclient.mvp.model.settings

import com.example.githubclient.mvp.model.entity.Settings
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.entity.room.RoomSettings

class RoomGithubSettings : IGithubSettings {
    override fun saveSettings(db: Database, settings: Settings) {
        db.settingsDao.saveSettings(
            RoomSettings(
                settings.sizeList,
                settings.sizeRepoList
            )
        )
    }

    override fun getSettings(db: Database): Settings =
        db.settingsDao.getSettings().let { Settings(it!!.sizeList, it.sizeRepoList) }
}