package com.example.githubclient

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.githubclient.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding get() = _binding!!
    private val presenter by moxyPresenter { MainPresenter(CounterModel()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCounter1.setOnClickListener { presenter.counterOneClick(Counters.COUNTER1) }
        binding.btnCounter2.setOnClickListener { presenter.counterTwoClick(Counters.COUNTER2) }
        binding.btnCounter3.setOnClickListener { presenter.counterThreeClick(Counters.COUNTER3) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setButtonOneText(text: String) {
        binding.btnCounter1.text = text
    }

    override fun setButtonTwoText(text: String) {
        binding.btnCounter2.text = text
    }

    override fun setButtonThreeText(text: String) {
        binding.btnCounter3.text = text
    }
}