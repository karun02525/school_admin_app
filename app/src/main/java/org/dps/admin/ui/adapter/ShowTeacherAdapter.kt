package org.dps.admin.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_show_teacher.view.*
import org.dps.admin.R
import org.dps.admin.model.TeacherData


class ShowTeacherAdapter(var list: List<TeacherData> = listOf()) : RecyclerView.Adapter<ShowTeacherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_show_teacher, parent, false))
    
    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(model: TeacherData) {
            itemView.run {
               // Glide.with(context).load(model.avatar).into(ivProfileUser)
                tvUserContactName.text = model.fname + " " + model.lname
                tvMess.text = model.phone +" "+ model.qualification
            }
        }
    }
}