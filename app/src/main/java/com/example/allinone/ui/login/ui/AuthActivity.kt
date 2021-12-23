package com.example.allinone.ui.login.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.allinone.R
import dagger.android.support.DaggerAppCompatActivity


class AuthActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

//        setFragmentTitles()
    }

    private fun setFragmentTitles() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.authFragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.loginFragment, R.id.registerFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}