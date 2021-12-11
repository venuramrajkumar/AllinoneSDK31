package com.example.allinone.ui.ui.launchnews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.allinone.R
import com.example.allinone.databinding.ActivityNavigationHostBinding
import com.example.allinone.ui.db.ArticleDatabase
import com.example.allinone.ui.repository.NewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView


class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationHostBinding

     lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
//TODO : refactor this

        val repository = NewsRepository(ArticleDatabase(this.applicationContext))
        val viewModelProviderFactory = ViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
        setBottomNavigationView()
    }



    private fun setBottomNavigationView() {
        val bottomNavigationView: BottomNavigationView = binding.navView

        //        val navController = findNavController(R.id.newsNavHostFragment) //use this when u use <fragment for host
        //As we are using FragmentContainerView as host we have to get navController like this.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_breakingnews, R.id.navigation_savednews, R.id.navigation_searchnews
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }

}