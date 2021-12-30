package com.example.allinone.ui.news.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.allinone.databinding.ActivityMainBinding
import com.example.allinone.ui.login.data.UserPreferences
import com.example.allinone.ui.login.home.HomeActivity
import com.example.allinone.ui.login.ui.AuthActivity
import com.example.allinone.ui.news.ui.launchnews.NewsActivity
import com.example.allinone.ui.notification.NotificationDemoActivity
import com.example.allinone.ui.utils.startNewActivity

class MainDemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.newsapp.setOnClickListener {
            startActivity(Intent(this, NewsActivity::class.java))
        }

        binding.notifications.setOnClickListener {
            startActivity(Intent(this, NotificationDemoActivity::class.java))
        }

        binding.login.setOnClickListener {
            val userPreferences = UserPreferences(this.application)
            userPreferences.accessToken.asLiveData().observe(this, Observer {
                val activity = if (it == null) AuthActivity::class.java else HomeActivity::class.java
                startNewActivity(activity)
            })

        }

    }
}