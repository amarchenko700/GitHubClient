package com.example.githubclient.ui.fragment

import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentUserRepoBinding
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.presenter.UserRepoPresenter
import com.example.githubclient.navigation.IScreens
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.fragment.view.UserRepoView
import com.github.terrakok.cicerone.Router
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class UserRepoFragment(private val githubUserRepo: GithubUserRepository) :
    BaseFragment<FragmentUserRepoBinding>(
        FragmentUserRepoBinding::inflate
    ), UserRepoView, BackButtonListener {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var screens: IScreens

    val presenter: UserRepoPresenter by moxyPresenter {
        UserRepoPresenter(router, screens)
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