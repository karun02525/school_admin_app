package org.dps.admin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.dps.admin.R

class HomeFragment : Fragment() {

    companion object {
        @JvmStatic
        fun instance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        marqueeText.text = createMarqueeText();
        marqueeText.requestFocus()
    }

    private fun createMarqueeText(): String {
        val text = StringBuilder()
        text.append("Dear parents Greetings of the day Hope you all are doing well. Stay healthy & Safe")
        text.append("This is to inform you that school has done some changes in the *FEE payments*…")
        return text.toString()
    }
}