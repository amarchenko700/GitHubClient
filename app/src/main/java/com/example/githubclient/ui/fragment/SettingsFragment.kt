package com.example.githubclient.ui.fragment

import com.example.githubclient.databinding.FragmentSettingsBinding
import com.example.githubclient.mvp.presenter.SettingsPresenter
import com.example.githubclient.ui.activity.BackButtonListener
import com.example.githubclient.ui.fragment.view.SettingsView
import moxy.ktx.moxyPresenter

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate),
    SettingsView, BackButtonListener {

    val presenter: SettingsPresenter by moxyPresenter {
        SettingsPresenter().apply {
//            App.instance.initSettingsSubcomponent().inject(this)
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun backPressed() = presenter.backPressed()

    override fun saveSettings() {
//        presenter.callbackSaveSettings.invoke()
    }
}