package org.dps.admin.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.popwoot.carouselbanner.interfaces.CarouselImageFactory
import org.dps.admin.R
import java.text.SimpleDateFormat
import java.util.*


fun log(s:String){
    Log.d("TAGS", "log: $s")
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showSnack(message: String): Snackbar {
    val sb = Snackbar.make((this as Activity).findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_LONG)
    sb.view.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    val textView = sb.view.findViewById<TextView>(R.id.snackbar_text)
    textView.setTextColor(Color.RED)
    sb.show()
    return sb
}

fun Context.hideSoftKeyboard() {
    try {
        val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow((this as Activity).currentFocus!!.windowToken, 0)
    } catch (e: Exception) {
    }
}
//Custom Snackbar
fun Context.messToast(message: String): Snackbar {
    val sb = Snackbar.make((this as Activity).findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_LONG)
    sb.view.setBackgroundColor(ContextCompat.getColor(this, R.color.design_default_color_primary_dark))
    val textView = sb.view.findViewById<TextView>(R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)
    sb.show()
    return sb
}

fun Context.startNewActivity(cls: Class<*>) {
    this.startActivity(Intent(this, cls))
}

fun AppCompatActivity.startNewActivityFinish(cls: Class<*>) {
    this.startActivity(Intent(this, cls))
    finish()
}


class ImageFactory : CarouselImageFactory {
    override fun onLoadFactory(url: String, view: ImageView) {
        Glide.with(view).load(url).into(view)
    }
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}