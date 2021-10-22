package com.resurrection.movies.ui.main.util_adapters

import androidx.databinding.ViewDataBinding
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.ui.base.BaseAdapter

class MovieItemAdapter<T, viewDataBinding : ViewDataBinding>(
    private var mLayoutResource: Int,
    private var mList: ArrayList<T>,
    private var mItemId: Int,
    private var mOnItemClick: (T) -> Unit
) : BaseAdapter<T, viewDataBinding>(mLayoutResource, mList, mItemId, mOnItemClick) {

    fun sortAToZ() {
        if (currentList.size != 1) {
            val mutable: MutableList<SearchItem> =
                currentList.toMutableList() as MutableList<SearchItem>
            mutable.sortBy { it.title }
            currentList = mutable.toList() as ArrayList<SearchItem> as ArrayList<T>
            notifyChanged()
        }
    }

    fun sortZToA() {
        if (currentList.size != 1) {
            val mutable: MutableList<SearchItem> =
                currentList.toMutableList() as MutableList<SearchItem>
            mutable.sortBy { it.title }
            mutable.reverse()
            currentList = mutable.toList() as ArrayList<SearchItem> as ArrayList<T>
            notifyChanged()
        }
    }

    fun sortOldToNew() {
        if (currentList.size != 1) {
            val mutable: MutableList<SearchItem> =
                currentList.toMutableList() as MutableList<SearchItem>
            mutable.sortBy { it.year }
            currentList = mutable.toList() as ArrayList<SearchItem> as ArrayList<T>
            notifyChanged()
        }
    }

    fun sortNewToOld() {
        if (currentList.size != 1) {
            val mutable: MutableList<SearchItem> =
                currentList.toMutableList() as MutableList<SearchItem>
            mutable.sortBy { it.year }
            mutable.reverse()
            currentList = (mutable.toList() as ArrayList<SearchItem>) as ArrayList<T>
            notifyChanged()
        }

    }
}