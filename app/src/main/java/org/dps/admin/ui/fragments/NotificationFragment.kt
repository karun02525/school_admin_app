package org.dps.admin.ui.fragments

//import com.facebook.drawee.backends.pipeline.Fresco
//import kotlinx.android.synthetic.main.fragment_home.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import org.dps.admin.R

class NotificationFragment : Fragment() {

    companion object {
        @JvmStatic
        fun instance() = NotificationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}