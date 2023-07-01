package com.example.githubclient.ui.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}