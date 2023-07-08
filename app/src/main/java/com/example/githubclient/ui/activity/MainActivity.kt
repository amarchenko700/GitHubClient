package com.example.githubclient.ui.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.githubclient.App
import com.example.githubclient.R
import com.example.githubclient.databinding.ActivityMainBinding
import com.example.githubclient.mvp.presenter.MainPresenter
import com.example.githubclient.ui.fragment.view.MainView
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val presenter by moxyPresenter {
        MainPresenter().apply {
            App.instance.appComponent.inject(this)
        }
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
            } else {
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
        App.instance.appComponent.inject(this)
        binding.bottomNavigationMenu.setOnItemSelectedListener {
            presenter.onBottomNavigationItemClick(it.itemId)
        }
        //Creation().exec()
        //Operators().exec()
        //Sources().exec()
        //BackPressure().exec()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}