package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.ui.fragment.view.UserRepoView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class RepositoryPresenter(val githubRepository: GithubUserRepository) :
    MvpPresenter<UserRepoView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setTitle(githubRepository.name)
        viewState.setForksCount(githubRepository.forksCount)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}