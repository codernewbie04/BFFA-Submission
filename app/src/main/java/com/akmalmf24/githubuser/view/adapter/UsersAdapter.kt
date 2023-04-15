package com.akmalmf24.githubuser.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.akmalmf24.githubuser.R
import com.akmalmf24.githubuser.abstraction.base.BaseRecyclerViewAdapter
import com.akmalmf24.githubuser.core.data.remote.response.Users
import com.akmalmf24.githubuser.databinding.ItemUsersBinding


/**
 * Created by Akmal Muhamad Firdaus on 05/03/2023 00:30.
 * akmalmf007@gmail.com
 */
class UsersAdapter(val showIcon: Boolean): BaseRecyclerViewAdapter<UsersAdapter.VHolder, Users>(){

    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemUsersBinding.bind(itemView)
        @SuppressLint("SetTextI18n")
        fun onBind(data: Users){
            binding.apply {
                imageIcon.visibility = if(showIcon) View.VISIBLE else View.GONE
                textUsername.text = data.login
                textRepos.text = data.htmlUrl
                textId.text = data.id.toString()
                userImage.load(data.avatarUrl) {
                    placeholder(R.color.gray)
                }
                root.setOnClickListener{
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: VHolder, item: Users, position: Int) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false))
    }

    override fun getItemCount(): Int {
        return  items.size
    }
}