package com.example.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.databinding.ItemUserBinding
import com.example.githubclient.mvp.presenter.list.IUserListPresenter
import com.example.githubclient.ui.fragment.view.list.UserItemView
import com.example.githubclient.ui.image.IImageLoader
import javax.inject.Inject

class UsersRVAdapter(val presenter: IUserListPresenter) :
    RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    inner class ViewHolder(private val vb: ItemUserBinding) : RecyclerView.ViewHolder(vb.root),
        UserItemView {
        override var pos = -1

        override fun setLogin(text: String) = with(vb) {
            tvLogin.text = text
        }

        override fun loadAvatar(url: String) {
            imageLoader.loadInto(url, vb.ivAvatar)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply {
            pos = position
        })
}