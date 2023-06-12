package com.example.githubclient.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentUserBinding
import com.example.githubclient.mvp.model.GithubUser
import com.example.githubclient.mvp.presenter.UserPresenter
import com.example.githubclient.mvp.view.UserView
import com.example.githubclient.ui.activity.BackButtonListener
import moxy.ktx.moxyPresenter

class UserFragment(private val githubUser: GithubUser) :
    BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate), UserView,
    BackButtonListener {


    val presenter: UserPresenter by moxyPresenter {
        UserPresenter(App.instance.router, App.instance.androidScreens)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.text = githubUser.login
    }

    companion object {
        fun newInstance(githubUser: GithubUser) = UserFragment(githubUser)
    }

    override fun backPressed() = presenter.backPressed()
}