package com.example.githubclient.ui.fragment.view

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}