package org.dps.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import org.dps.admin.ui.create.CreateClassActivity
import org.dps.admin.ui.create.CreateParentActivity
import org.dps.admin.ui.create.CreateStudentActivity
import org.dps.admin.ui.create.CreateTeacherActivity
import org.dps.admin.ui.fragments.CreateFragment
import org.dps.admin.ui.fragments.HomeFragment
import org.dps.admin.ui.fragments.NotificationFragment
import org.dps.admin.utils.startNewActivity
import java.util.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        initViews()
        initComponentsNavHeader()
        loadFragment(HomeFragment.instance())
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)!!.setTitle(0)
    }

    private fun initViews() {
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = false
        toggle.toolbarNavigationClickListener =
            View.OnClickListener { view: View? -> drawer.openDrawer(GravityCompat.START) }
        toggle.setHomeAsUpIndicator(R.drawable.ic_drawer)
        toggle.syncState()

    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            return true
        }
        return false
    }

    @SuppressLint("NonConstantResourceId")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.action_home -> fragment = HomeFragment.instance()
            R.id.action_assgin -> fragment = CreateFragment.instance()
            R.id.action_notifications -> fragment = NotificationFragment.instance()
            R.id.action_profile -> fragment = HomeFragment.instance()
        }
        return loadFragment(fragment)
    }

    private fun initComponentsNavHeader() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(object :
                NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.create_class -> {
                        startNewActivity(CreateClassActivity::class.java)
                    }
                    R.id.create_parent -> {
                        startNewActivity(CreateParentActivity::class.java)
                    }
                    R.id.create_student -> {
                        startNewActivity(CreateStudentActivity::class.java)
                    }
                    R.id.create_teacher -> {
                        startNewActivity(CreateTeacherActivity::class.java)
                    }
                    R.id.nav_result -> Pesan("My Account")
                    R.id.nav_support -> Pesan("Support")
                    R.id.nav_help -> Pesan("Help")
                    R.id.nav_logout -> Pesan("logout")

                }
                val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
                drawer.closeDrawer(GravityCompat.START)
                return true
            }

            private fun Pesan(pesan: String) {
                Toast.makeText(this@MainActivity, pesan, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}