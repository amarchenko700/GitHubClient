package com.example.githubclient.mvp.presenter

import com.example.githubclient.di.user.module.IUserScopeContainer
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.navigation.IScreens
import com.example.githubclient.ui.fragment.view.UsersView
import com.example.githubclient.ui.fragment.view.list.UserItemView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named

class UsersPresenter :
    MvpPresenter<UsersView>() {

    val usersListPresenter = UsersListPresenter()

    @Inject
    lateinit var usersRepo: IGithubUsersRepo

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Named("mainThread")
    @Inject
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var userScopeContainer: IUserScopeContainer

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

    override fun onDestroy() {
        userScopeContainer.releaseUserScope()
        super.onDestroy()
    }
}