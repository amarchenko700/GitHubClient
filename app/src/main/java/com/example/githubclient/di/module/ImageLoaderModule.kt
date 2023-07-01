package com.example.githubclient.di.module

import android.widget.ImageView
import com.example.githubclient.ui.image.GlideImageLoader
import com.example.githubclient.ui.image.IImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageLoaderModule {
    @Singleton
    @Provides
    fun imageLoader(): IImageLoader<ImageView> = GlideImageLoader()
}