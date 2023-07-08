package com.example.githubclient.ui.fragment

import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentUserRepoBinding
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.presenter.RepositoryPresenter
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.fragment.view.UserRepoView
import moxy.ktx.moxyPresenter

class UserRepoFragment(private val githubUserRepo: GithubUserRepository) :
    BaseFragment<FragmentUserRepoBinding>(
        FragmentUserRepoBinding::inflate
    ), UserRepoView, BackButtonListener {

    val presenter: RepositoryPresenter by moxyPresenter {
        RepositoryPresenter(githubUserRepo).apply {
            App.instance.repositorySubcomponent?.inject(this)
        }
    }

    override fun backPressed() = presenter.backPressed()

    override fun setTitle(text: String) {
        binding.repoName.text = text
    }

    override fun setForksCount(forksCount: Int) {
        binding.repoForks.text = forksCount.toString()
    }

    companion object {
        fun newInstance(githubUserRepo: GithubUserRepository) =
            UserRepoFragment(githubUserRepo)
    }
}