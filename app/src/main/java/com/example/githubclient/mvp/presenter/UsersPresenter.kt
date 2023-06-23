package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.GithubUser
import com.example.githubclient.mvp.model.GithubUsersRepo
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.mvp.view.UsersView
import com.example.githubclient.mvp.view.list.UserItemView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
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

    private val githubUsersObserver = object : Observer<GithubUser> {
        override fun onSubscribe(d: Disposable) {
            println("onSubscribe")
        }

        override fun onError(e: Throwable) {
            println("onError: ${e.message}")
        }

        override fun onComplete() {
            viewState.updateList()
        }

        override fun onNext(githubUser: GithubUser) {
            userListPresenter.users.add(githubUser)
        }

    }

    private fun loadData() {
//        val users = usersRepo.getUsers()
//        userListPresenter.users.addAll(users)
//        viewState.updateList()
        usersRepo.getRXUsers().subscribe(githubUsersObserver)
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