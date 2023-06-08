package com.example.githubclient

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.githubclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainView {

    private var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding get() = _binding!!
    val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listener = object : OnCounterClickListener {
            override fun onClick(view: View, counter: Counters) {
                presenter.counterClick(counter)
            }
        }

        binding.btnCounter1.setOnClickListener { view -> listener.onClick(view, Counters.COUNTER1) }
        binding.btnCounter2.setOnClickListener { view -> listener.onClick(view, Counters.COUNTER2) }
        binding.btnCounter3.setOnClickListener { view -> listener.onClick(view, Counters.COUNTER3) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setButtonText(counter: Counters, text: String) {
        when(counter){
            Counters.COUNTER1 -> binding.btnCounter1.text = text
            Counters.COUNTER2 -> binding.btnCounter2.text = text
            Counters.COUNTER3 -> binding.btnCounter3.text = text
        }
    }
}