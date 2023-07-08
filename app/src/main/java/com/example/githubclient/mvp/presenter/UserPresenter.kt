package com.example.githubclient.mvp.presenter

import com.example.githubclient.di.repository.module.IRepositoryScopeContainer
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.GithubUserRepository
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo
import com.example.githubclient.mvp.presenter.listUserRepo.IUserRepoListPresenter
import com.example.githubclient.navigation.IScreens
import com.example.githubclient.ui.fragment.view.UserView
import com.example.githubclient.ui.fragment.view.listUserRepo.UserRepoItemView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named

class UserPresenter(
    private val githubUser: GithubUser
) : MvpPresenter<UserView>() {

    val userRepoListPresenter = UserListRepoPresenter()

    @Inject
    lateinit var userRepositoriesRepo: IGithubRepositoriesRepo

    @Inject
    lateinit var router: Router

    @Named("mainThread")
    @Inject
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var repositoryScopeContainer: IRepositoryScopeContainer

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
        userRepositoriesRepo.getRepositories(githubUser)
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

    override fun onDestroy() {
        repositoryScopeContainer.releaseRepositoryScope()
        super.onDestroy()
    }
}
