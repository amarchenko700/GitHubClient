package com.example.githubclient.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.App
import com.example.githubclient.databinding.FragmentUsersBinding
import com.example.githubclient.mvp.model.api.ApiHolder
import com.example.githubclient.mvp.model.entity.room.Database
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo
import com.example.githubclient.mvp.presenter.UsersPresenter
import com.example.githubclient.mvp.view.UsersView
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.adapter.UsersRVAdapter
import com.example.githubclient.ui.image.GlideImageLoader
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.ktx.moxyPresenter

class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate), UsersView,
    BackButtonListener {

    private var adapter: UsersRVAdapter? = null

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubUsersRepo(ApiHolder.api, App.networkStatus, Database.getInstance()),
            App.instance.router,
            App.instance.androidScreens
        )
    }

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun init() {
        adapter = UsersRVAdapter(presenter.usersListPresenter, GlideImageLoader())
        binding.rvUsers.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()

}