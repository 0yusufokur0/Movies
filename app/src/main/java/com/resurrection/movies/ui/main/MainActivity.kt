package com.resurrection.movies.ui.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.resurrection.movies.R
import com.resurrection.movies.databinding.ActivityMainBinding
import com.resurrection.movies.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {


/*    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    *//*    binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)*//*


    }*/

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
/*
        val navView: BottomNavigationView = binding.navView
*/

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favorite
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }
}