package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.GithubUser
import com.example.githubclient.mvp.model.GithubUsersRepo
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.mvp.view.MainView
import com.example.githubclient.mvp.view.list.UserItemView
import moxy.MvpPresenter

class MainPresenter (private val usersRepo: GithubUsersRepo): MvpPresenter<MainView>(){

    val userListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        userListPresenter.itemClickListener = {itemView->
            //TODO homework
        }
    }

    private fun loadData(){
        val users = usersRepo.getUsers()
        userListPresenter.users.addAll(users)
        viewState.updateList()
    }

    class UsersListPresenter :IUserListPresenter{
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }

        override fun getCount() = users.size

    }
}