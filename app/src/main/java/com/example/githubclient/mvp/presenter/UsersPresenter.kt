package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.mvp.view.UsersView
import com.example.githubclient.mvp.view.list.UserItemView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class UsersPresenter(
    private val uiScheduler: Scheduler,
    private val usersRepo: IGithubUsersRepo,
    private val router: Router,
    private val screens: IScreens
) :
    MvpPresenter<UsersView>() {

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            val gitHubUser: GithubUser = usersListPresenter.users[itemView.pos]
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
            usersListPresenter.users.add(githubUser)
        }

    }

    private fun loadData() {
        usersRepo.getUsers()
            .observeOn(uiScheduler)
            .subscribe({ repos ->
                usersListPresenter.users.clear()
                usersListPresenter.users.addAll(repos)
                viewState.updateList()
            }, {
                println("onError Completable: ${it.message}")
            })
    }

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
            user.avatarUrl?.let {
                view.loadAvatar(it)
            }
        }

        override fun getCount() = users.size

    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}