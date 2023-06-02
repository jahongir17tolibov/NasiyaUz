package com.example.nasiyauz.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nasiyauz.databinding.MainViewItemLyBinding
import com.example.nasiyauz.databinding.PlusButtonItemLyBinding
import com.example.nasiyauz.data.models.UserModel
import com.example.nasiyauz.ui.screens.BaseFragmentDirections

class MainAdapter : ListAdapter<UserModel, RecyclerView.ViewHolder>(MainDiffUtil()) {

    inner class ItemViewHolder(val mainBinding: MainViewItemLyBinding) :
        RecyclerView.ViewHolder(mainBinding.root) {

        fun bind(result: UserModel) = mainBinding.apply {
            customersName.text = result.userName
            customersNumber.text = result.usersNumber
        }

    }

    inner class ExtraViewHolder(private val buttonBinding: PlusButtonItemLyBinding) :
        RecyclerView.ViewHolder(buttonBinding.root)

    internal class MainDiffUtil : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.userName == newItem.userName
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
            areItemsTheSame(oldItem, newItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            EXTRA_ITEM_VIEW_TYPE -> {
                ExtraViewHolder(
                    PlusButtonItemLyBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            REGULAR_ITEM_VIEW_TYPE -> {
                ItemViewHolder(
                    MainViewItemLyBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(getItem(position - 1))
            is ExtraViewHolder -> holder.itemView.setOnClickListener {
                val action = BaseFragmentDirections.actionBaseFragmentToNoteFragment()
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) {
        EXTRA_ITEM_VIEW_TYPE
    } else {
        REGULAR_ITEM_VIEW_TYPE
    }

    override fun getItemCount(): Int = currentList.size + 1

    companion object {
        private const val EXTRA_ITEM_VIEW_TYPE = 0
        private const val REGULAR_ITEM_VIEW_TYPE = 1
    }

}