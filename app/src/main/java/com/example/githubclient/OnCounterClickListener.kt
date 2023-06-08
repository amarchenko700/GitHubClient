package com.example.githubclient

import android.view.View

interface OnCounterClickListener {
    fun onClick(view: View, counter: Counters)
}