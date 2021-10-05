package org.dps.admin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.fragment_create.*
import org.dps.admin.R
import org.dps.admin.model.CreateMenuModel
import org.dps.admin.ui.adapter.CreateMenuAdapter

class CreateFragment : Fragment() {
    private val mAdapter by lazy { CreateMenuAdapter() }


    companion object {
        @JvmStatic
        fun instance() = CreateFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val list: ArrayList<CreateMenuModel> = arrayListOf()
        list.run {
            add(CreateMenuModel(1, "See Students", R.drawable.ic_user))
            add(CreateMenuModel(2, "See Teacher", R.drawable.ic_open_book))
            add(CreateMenuModel(3, "Assign Teacher", R.drawable.ic_noticeboard))
            add(CreateMenuModel(4, "Assign Roll No", R.drawable.ic_noticeboard))
        }
        mAdapter.list = list
        rv_create_menu.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
    }
}