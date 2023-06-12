package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.GithubUser
import com.example.githubclient.mvp.model.GithubUsersRepo
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.mvp.view.UsersView
import com.example.githubclient.mvp.view.list.UserItemView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class UsersPresenter(
    private val usersRepo: GithubUsersRepo,
    private val router: Router,
    private val screens: IScreens
) :
    MvpPresenter<UsersView>() {

    val userListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        userListPresenter.itemClickListener = { itemView ->
            val gitHubUser: GithubUser = userListPresenter.users[itemView.pos]
            router.navigateTo(screens.githubUser(gitHubUser))
        }
    }

    private fun loadData() {
        val users = usersRepo.getUsers()
        userListPresenter.users.addAll(users)
        viewState.updateList()
    }

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }

        override fun getCount() = users.size

    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}