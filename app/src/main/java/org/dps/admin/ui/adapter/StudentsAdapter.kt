package org.dps.admin.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_show_students.view.*
import org.dps.admin.R
import org.dps.admin.model.StudentData
import org.dps.admin.network.Const
import org.dps.admin.ui.assign.AssignRollNoActivity
import org.dps.admin.ui.create.StudentsUploadDocumentsActivity
import org.dps.admin.utils.startNewActivity


class StudentsAdapter(var list: List<StudentData> = listOf()) : RecyclerView.Adapter<StudentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_show_students, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(model: StudentData) {
            itemView.run {

                if(model.avatar.isNullOrEmpty()){
                    editPic.setOnClickListener {
                        context.startActivity(
                            Intent(context, StudentsUploadDocumentsActivity::class.java)
                            .putExtra("id",model.id)
                        )
                    }
                    editPic.visibility=View.VISIBLE
                }else{
                    editPic.visibility=View.INVISIBLE
                }

                Picasso.get()
                    .load("${Const.BASE_URL}/${model.avatar}")
                    .into(iv_user_pic, object : Callback {
                        override fun onSuccess() {

                        }
                        override fun onError(e: Exception?) {
                            iv_user_pic.setImageResource(R.drawable.profile_pic)
                        }
                    })

                tvUserContactName.text = model.fname +" "+model.lname
                tv_class.text ="Class Name: " +model.classes?.name

                if(model.rollno==0) {
                    tv_roll_no.text = "Roll No: Pending.. "
                    tv_roll_no.setTextColor(Color.RED)
                    tv_view_roll.visibility=View.VISIBLE
                    tv_view_roll.setOnClickListener { context.startNewActivity(AssignRollNoActivity::class.java)  }
                }else{
                    tv_roll_no.setTextColor(Color.parseColor("#673AB7"))
                    tv_roll_no.text = "Roll No: " + model.rollno
                    tv_view_roll.visibility=View.GONE
                }

                tv_parent.text = model.father_name
            }
        }
    }
}