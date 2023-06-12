package com.example.githubclient.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.mvp.presenter.MainPresenter
import com.example.githubclient.databinding.ActivityMainBinding
import com.example.githubclient.mvp.model.GithubUsersRepo
import com.example.githubclient.mvp.view.MainView
import com.example.githubclient.ui.adapter.UsersRVAdapter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding get() = _binding!!
    private val presenter by moxyPresenter { MainPresenter(GithubUsersRepo()) }
    private var adapter : UsersRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun init() {
        adapter = UsersRVAdapter(presenter.userListPresenter)
        binding.rvUsers.let{
            it.layoutManager = LinearLayoutManager(this@MainActivity)
            it.adapter= adapter
        }
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

}