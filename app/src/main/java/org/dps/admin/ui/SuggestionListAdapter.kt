package org.dps.admin.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.dps.admin.R
import org.dps.admin.model.SuggestionAddress
import kotlinx.android.synthetic.main.adapter_suggestion_list.view.*

class SuggestionListAdapter (var list: List<SuggestionAddress>) : RecyclerView.Adapter<SuggestionListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_suggestion_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: SuggestionAddress) {
            itemView.apply {
                suggestion_title.text = model.city
                suggestion_sub_title.text = model.addressString
            }.setOnClickListener {
                Toast.makeText(itemView.context, model.id +" "+model.city +" "+model.addressString, Toast.LENGTH_SHORT).show();
            }
        }
    }

}