package org.dps.admin.ui

import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import org.dps.admin.MainActivity
import org.dps.admin.R
import org.dps.admin.ui.create.TestingDashboard
import org.dps.admin.utils.startNewActivityFinish

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startNewActivityFinish(MainActivity::class.java)
        }, 100)


    }
}