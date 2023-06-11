package com.example.githubclient

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun setButtonOneText(text: String)
    fun setButtonTwoText(text: String)
    fun setButtonThreeText(text: String)
}