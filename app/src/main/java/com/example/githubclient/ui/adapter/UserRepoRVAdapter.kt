package com.example.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.databinding.ItemUserRepoBinding
import com.example.githubclient.mvp.presenter.listUserRepo.IUserRepoListPresenter
import com.example.githubclient.ui.fragment.view.listUserRepo.UserRepoItemView

class UserRepoRVAdapter(val presenter: IUserRepoListPresenter) :
    RecyclerView.Adapter<UserRepoRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val vb: ItemUserRepoBinding) : RecyclerView.ViewHolder(vb.root),
        UserRepoItemView {

        override var pos = -1

        override fun setNameRepo(nameRepo: String) = with(vb) {
            tvRepo.text = nameRepo
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemUserRepoBinding.inflate(
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