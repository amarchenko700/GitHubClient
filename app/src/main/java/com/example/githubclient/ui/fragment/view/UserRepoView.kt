package com.example.githubclient.ui.fragment.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserRepoView : MvpView {
    fun setTitle(text: String)
    fun setForksCount(forksCount: Int)
}