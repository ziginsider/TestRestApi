package io.github.ziginsider.testrestapi

import android.support.annotation.LayoutRes
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class RecyclerViewAdapter(@LayoutRes private val layoutResId: Int) :
    ListAdapter<UserWithGifsModel, RecyclerViewAdapter.ViewHolder>(ModelUserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutResId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val avatar: ImageView = itemView.findViewById(R.id.userPhoto)
        private val gif1: ImageView = itemView.findViewById(R.id.userGif1)
        private val gif2: ImageView = itemView.findViewById(R.id.userGif2)
        private val gif3: ImageView = itemView.findViewById(R.id.userGif3)
        private val userName: TextView = itemView.findViewById(R.id.userName)
        private val userLastName: TextView = itemView.findViewById(R.id.userLastName)
        private val userEmail: TextView = itemView.findViewById(R.id.userEmail)
        private val userPhone: TextView = itemView.findViewById(R.id.userPhone)
        private val userGender: TextView = itemView.findViewById(R.id.userGender)

        private val requestBuilder = Glide.with(itemView.context)

        fun bind(user: UserWithGifsModel) {
            requestBuilder.load(user.photoUrl).into(avatar)
            requestBuilder.load(user.gifOneUrl).into(gif1)
            requestBuilder.load(user.gifTwoUrl).into(gif2)
            requestBuilder.load(user.gifTreeUrl).into(gif3)
            userName.text = user.name
            userLastName.text = user.name
            userEmail.text = user.lastName
            userPhone.text = user.phone
            userGender.text = user.gender
        }
    }
}