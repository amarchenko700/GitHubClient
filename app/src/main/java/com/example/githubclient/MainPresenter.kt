package com.example.githubclient

import moxy.MvpPresenter

class MainPresenter (private val model : CounterModel): MvpPresenter<MainView>(){

    fun counterOneClick(counters: Counters) {
        val nextValue = model.next(counters.indexValue)
        viewState.setButtonOneText(nextValue.toString())
    }

    fun counterTwoClick(counters: Counters) {
        val nextValue = model.next(counters.indexValue)
        viewState.setButtonTwoText(nextValue.toString())
    }

    fun counterThreeClick(counters: Counters) {
        val nextValue = model.next(counters.indexValue)
        viewState.setButtonThreeText(nextValue.toString())
    }
}