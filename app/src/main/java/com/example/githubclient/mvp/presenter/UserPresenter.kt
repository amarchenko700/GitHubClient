package com.example.githubclient.mvp.presenter

import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo
import com.example.githubclient.mvp.presenter.listUserRepo.IUserRepoListPresenter
import com.example.githubclient.mvp.view.UserView
import com.example.githubclient.mvp.view.listUserRepo.UserRepoItemView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter

class UserPresenter(
    private val uiScheduler: Scheduler,
    private val usersRepo: IGithubRepositoriesRepo,
    private val router: Router,
    private val screens: IScreens,
    private val githubUser: GithubUser
) : MvpPresenter<UserView>() {

    val userRepoListPresenter = UserListRepoPresenter()

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        userRepoListPresenter.itemClickListener = { itemView ->
            val gitHubUserRepo: GithubUserRepository =
                userRepoListPresenter.userRepositories[itemView.pos]
            router.navigateTo(screens.githubUserRepo(gitHubUserRepo))
        }
    }

    private fun loadData() {
        usersRepo.getRepositories(githubUser)
            .observeOn(uiScheduler)
            .subscribe({ repos ->
                userRepoListPresenter.userRepositories.clear()
                userRepoListPresenter.userRepositories.addAll(repos)
                viewState.updateList()
            })
    }

    class UserListRepoPresenter : IUserRepoListPresenter {
        val userRepositories = mutableListOf<GithubUserRepository>()

        override var itemClickListener: ((UserRepoItemView) -> Unit)? = null

        override fun bindView(view: UserRepoItemView) {
            val userRepo = userRepositories[view.pos]
            view.setNameRepo(userRepo.name)
        }

        override fun getCount() = userRepositories.size

    }
}
