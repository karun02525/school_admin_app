package org.dps.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main_dashboard.*
import org.dps.admin.ui.adapter.CustomExpandableListAdapter
import org.dps.admin.ui.fragments.CreateFragment
import org.dps.admin.ui.fragments.HomeFragment
import org.dps.admin.ui.fragments.NotificationFragment
import org.dps.admin.utils.MenuUtils
import org.dps.admin.utils.MenuUtils.navigate

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String> ? = null




    private var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)
        setToolbar()
        initViews()
        loadFragment(HomeFragment.instance())
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(0)
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

       val navHeader = nav_view.getHeaderView(0);
       val  tvMenuTitle = navHeader.findViewById<TextView>(R.id.tvMenuTitle)
        tvMenuTitle.text="hiii"
        expandableListView = navHeader.findViewById(R.id.expandableListView)
        expendable()
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

    fun expendable(){
        if (expandableListView != null) {
            val listData = MenuUtils.data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)

            expandableListView!!.setOnGroupExpandListener {
                  //  groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<String>)[groupPosition] + " List Expanded.", Toast.LENGTH_SHORT).show()
            }

            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
               // Toast.makeText(applicationContext, (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.", Toast.LENGTH_SHORT).show()
            }

            expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
              //  Toast.makeText(applicationContext, "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition), Toast.LENGTH_SHORT).show()
               val data=listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition)
                navigate(data)
                false
            }
        }
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