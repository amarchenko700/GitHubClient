package com.example.githubclient

class MainPresenter (val view : MainView){

    val model = CounterModel()

    fun counterClick(counter: Counters){
        val nextValue = model.next(counter.indexValue)
        view.setButtonText(counter, nextValue.toString())
    }
}