package com.example.allinone.ui.login.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.allinone.R
import com.example.allinone.ui.login.data.UserPreferences
import com.example.allinone.ui.login.ui.AuthActivity
import com.example.allinone.ui.utils.startNewActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    //private val viewModel by viewModels<HomeViewModel>()
    lateinit var viewModel : HomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
    }


    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        userPreferences.clear()
        startNewActivity(AuthActivity::class.java)
    }

}