package com.example.githubclient.ui.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.githubclient.App
import com.example.githubclient.R
import com.example.githubclient.databinding.ActivityMainBinding
import com.example.githubclient.mvp.presenter.MainPresenter
import com.example.githubclient.mvp.view.MainView
import com.example.githubclient.rxjava.Creation
import com.example.githubclient.rxjava.Operators
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!
    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router, App.instance.androidScreens)
    }

    private val navigator = AppNavigator(this, R.id.container)

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (supportFragmentManager.backStackEntryCount == 1) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(getString(R.string.exit_app))
                    .setMessage(getString(R.string.confirm_exit_app))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        val lastFragment = supportFragmentManager.fragments[0]
                        if (lastFragment is BackButtonListener) lastFragment.backPressed()
                        presenter.backClicked()
                        finish()
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }else{
                supportFragmentManager.fragments.forEach {
                    if (it is BackButtonListener && it.backPressed()) {
                        return
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        //Creation().exec()
        Operators().exec()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}