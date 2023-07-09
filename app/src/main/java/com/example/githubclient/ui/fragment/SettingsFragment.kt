package com.example.githubclient.ui.fragment

import com.example.githubclient.App
import com.example.githubclient.R
import com.example.githubclient.databinding.FragmentSettingsBinding
import com.example.githubclient.mvp.presenter.SettingsPresenter
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.activity.MainActivity
import com.example.githubclient.ui.fragment.view.SettingsView
import moxy.ktx.moxyPresenter

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate),
    SettingsView, BackButtonListener {

    val presenter: SettingsPresenter by moxyPresenter {
        SettingsPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun backPressed() = presenter.backPressed()

    private fun saveSettings() {
        presenter.saveSettings(
            binding.sizeList.text.toString().toInt(),
            binding.sizeRepoList.text.toString().toInt()
        )
    }

    override fun onPause() {
        super.onPause()
        Thread { saveSettings() }.start()
    }

    override fun init() {
        Thread {
            binding.sizeList.setText(presenter.getSizeUsersList().toString())
            binding.sizeRepoList.setText(presenter.getSizeUsersRepoList().toString())
        }.start()
        (requireActivity() as MainActivity).activateBottomNavigationMenu(R.id.settings)
    }
}