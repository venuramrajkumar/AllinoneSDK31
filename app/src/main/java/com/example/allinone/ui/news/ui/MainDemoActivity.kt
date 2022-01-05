package com.example.allinone.ui.news.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.allinone.R
import com.example.allinone.databinding.ActivityMainBinding
import com.example.allinone.ui.login.data.UserPreferences
import com.example.allinone.ui.login.home.HomeActivity
import com.example.allinone.ui.login.ui.AuthActivity
import com.example.allinone.ui.mulititableroom.RoomDbDemoActivity
import com.example.allinone.ui.news.ui.launchnews.NewsActivity
import com.example.allinone.ui.notification.NotificationDemoActivity
import com.example.allinone.ui.utils.startNewActivity

class MainDemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationDrawer()
        setListeners()
    }

    private fun setNavigationDrawer() {
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        //open and close for accessiblity.
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navDrawerView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.launchNews -> {
                    startActivity(Intent(this, NewsActivity::class.java))
                }
                R.id.launchAuth -> {
                    val userPreferences = UserPreferences(this.application)
                    userPreferences.accessToken.asLiveData().observe(this, Observer {
                        val activity =
                            if (it == null) AuthActivity::class.java else HomeActivity::class.java
                        startNewActivity(activity)
                    })
                }
                R.id.launchNoti -> {
                    startActivity(Intent(this, NotificationDemoActivity::class.java))
                }
                else -> {

                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    private fun setListeners() {

        binding.roomDemo.setOnClickListener {
            startActivity(Intent(this, RoomDbDemoActivity::class.java))
        }

        binding.newsapp.setOnClickListener {
            startActivity(Intent(this, NewsActivity::class.java))
        }

        binding.notifications.setOnClickListener {
            startActivity(Intent(this, NotificationDemoActivity::class.java))
        }

        binding.login.setOnClickListener {
            val userPreferences = UserPreferences(this.application)
            userPreferences.accessToken.asLiveData().observe(this, Observer {
                val activity =
                    if (it == null) AuthActivity::class.java else HomeActivity::class.java
                startNewActivity(activity)
            })

        }

    }

//    override fun onPostCreate(savedInstanceState: Bundle?) {
//        super.onPostCreate(savedInstanceState)
//        toggle.syncState()
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp()
//    }
}