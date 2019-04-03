package com.example.task.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import com.example.task.R
import com.example.task.business.PriorityBusiness
import com.example.task.business.UserBusiness
import com.example.task.constants.TaskConstants
import com.example.task.repository.PriorityCacheConstants
import com.example.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mPriorityBusiness: PriorityBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        mUserBusiness = UserBusiness(this)
        mPriorityBusiness = PriorityBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        loadPriorityCache()

        startDefaultFragment()
        formatUserName()
        formatDate()
    }

    private fun loadPriorityCache() {
        PriorityCacheConstants.setCache(mPriorityBusiness.getList())
    }

    private fun startDefaultFragment() {
        val fragment: Fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_done -> {
                val fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
                supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
            }
            R.id.nav_todo -> {
                val fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.TODO)
                supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
            }
            R.id.nav_logout -> {
                mUserBusiness.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun formatUserName() {
        val str = "Olá, ${mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)}!"
        textHello.text = str

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val header = navigationView.getHeaderView(0)
        val name = header.findViewById<TextView>(R.id.textName)
        val email = header.findViewById<TextView>(R.id.textEmail)

        name.text = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME )
        email.text = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)
    }

    private fun formatDate() {
        val c = Calendar.getInstance()
        val days = arrayOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado")
        val months = arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")

        val str = "${days[c.get(Calendar.DAY_OF_WEEK) - 1]}, ${c.get(Calendar.DAY_OF_MONTH)} de ${months[c.get(Calendar.MONTH)]}"
        textDateDescription.text = str
    }
}
