package io.github.ziginsider.testrestapi

import android.support.v7.util.DiffUtil

/**
 *
 * @author Aliaksei Kisel
 */
class ModelUserDiffCallback : DiffUtil.ItemCallback<UserWithGifsModel>() {

    override fun areItemsTheSame(oldItem: UserWithGifsModel, newItem: UserWithGifsModel): Boolean {
        return oldItem.lastName == newItem.lastName
    }

    override fun areContentsTheSame(oldItem: UserWithGifsModel, newItem: UserWithGifsModel): Boolean {
        return oldItem == newItem
    }
}