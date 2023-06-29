package com.example.githubclient.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentUserBinding
import com.example.githubclient.mvp.model.api.ApiHolder
import com.example.githubclient.mvp.model.entity.GithubUser
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo
import com.example.githubclient.mvp.presenter.UserPresenter
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.adapter.UserRepoRVAdapter
import com.example.githubclient.ui.fragment.view.UserView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.ktx.moxyPresenter

class UserFragment(private val githubUser: GithubUser) :
    BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate), UserView,
    BackButtonListener {

    private var adapter: UserRepoRVAdapter? = null

    val presenter: UserPresenter by moxyPresenter {
        UserPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubRepositoriesRepo(
                ApiHolder.api,
                App.networkStatus,
                Database.getInstance()
            ),
            App.instance.router,
            App.instance.androidScreens,
            githubUser
        )
    }

    companion object {
        fun newInstance(githubUser: GithubUser) = UserFragment(githubUser)
    }

    override fun backPressed() = presenter.backPressed()

    override fun init() {
        adapter = UserRepoRVAdapter(presenter.userRepoListPresenter)
        binding.login.text = githubUser.login
        binding.rvUserRepo.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }
}