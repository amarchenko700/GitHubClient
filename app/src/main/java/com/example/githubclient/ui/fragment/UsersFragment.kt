package com.example.githubclient.ui.fragment

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.App
import com.example.githubclient.R
import com.example.githubclient.databinding.FragmentUsersBinding
import com.example.githubclient.di.user.UserSubcomponent
import com.example.githubclient.mvp.presenter.UsersPresenter
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.activity.MainActivity
import com.example.githubclient.ui.adapter.UsersRVAdapter
import com.example.githubclient.ui.fragment.view.UsersView
import moxy.ktx.moxyPresenter

class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate), UsersView,
    BackButtonListener {

    var userSubcomponent: UserSubcomponent? = null
    private var adapter: UsersRVAdapter? = null

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter().apply {
            userSubcomponent = App.instance.initUserSubcomponent()
            userSubcomponent?.inject(this)
        }
    }

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun init() {
        adapter = UsersRVAdapter(presenter.usersListPresenter).apply {
            userSubcomponent?.inject(this)
        }
        binding.rvUsers.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
        (requireActivity() as MainActivity).activateBottomNavigationMenu(R.id.usersList)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData()
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun showError(error: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Ошибка")
            .setMessage(error)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun backPressed() = presenter.backPressed()

}