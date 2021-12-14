package com.example.allinone.ui.news.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.allinone.databinding.ActivityMainBinding
import com.example.allinone.ui.news.ui.launchnews.NewsActivity

class MainDemoActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

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

    }
}