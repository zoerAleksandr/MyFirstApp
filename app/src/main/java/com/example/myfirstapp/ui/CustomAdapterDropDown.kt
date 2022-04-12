package com.example.myfirstapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.myfirstapp.databinding.ItemDropDownBtnRemoveBinding

class CustomAdapterDropDown(
    context: Context,
    resources: Int,
    private var list: MutableList<String>
) :
    ArrayAdapter<String>(context, resources, list) {
    private var listAllItem = mutableListOf<String>()
    override fun getCount() = list.size

    override fun getItem(position: Int) = list[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemDropDownBtnRemoveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.text.text = getItem(position)
        return binding.root
    }

    override fun getFilter() = ListFilter()

    inner class ListFilter : Filter() {
        private val lock = Any()
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val result = FilterResults()
            val listAllItem = mutableListOf<String>()
            synchronized(lock) {
                listAllItem.addAll(list)
            }
            if (constraint == null || constraint.isEmpty()) {
                synchronized(lock) {
                    result.values = listAllItem
                    result.count = listAllItem.size
                }
            } else {
                val matchValues = mutableListOf<String>()
                val searchStrLowerCase = constraint.toString().lowercase()
                for (item in listAllItem) {
                    if (item.lowercase().startsWith(searchStrLowerCase)) {
                        matchValues.add(item)
                    }
                }
                result.values = matchValues
                result.count = matchValues.size
            }
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values != null) {
                list = results.values as MutableList<String>
            }
            if (results != null && results.count > 0) {
                clear()
                notifyDataSetChanged()
            }
            else notifyDataSetInvalidated()
        }
    }

}
