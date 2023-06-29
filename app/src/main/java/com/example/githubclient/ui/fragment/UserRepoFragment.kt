package com.example.githubclient.ui.fragment

import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentUserRepoBinding
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.presenter.UserRepoPresenter
import com.example.githubclient.mvp.view.UserRepoView
import com.example.githubclient.ui.activity.BackButtonListener
import moxy.ktx.moxyPresenter

class UserRepoFragment(private val githubUserRepo: GithubUserRepository) :
    BaseFragment<FragmentUserRepoBinding>(
        FragmentUserRepoBinding::inflate
    ), UserRepoView, BackButtonListener {

    val presenter: UserRepoPresenter by moxyPresenter {
        UserRepoPresenter(App.instance.router, App.instance.androidScreens)
    }

    override fun backPressed() = presenter.backPressed()

    override fun init() {
        binding.repoName.text = githubUserRepo.name
        binding.repoForks.text = githubUserRepo.forksCount.toString()
    }

    companion object {
        fun newInstance(githubUserRepo: GithubUserRepository) = UserRepoFragment(githubUserRepo)
    }
}