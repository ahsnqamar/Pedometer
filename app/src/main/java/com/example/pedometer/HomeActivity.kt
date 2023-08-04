package com.example.pedometer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pedometer.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        initListener()

    }

    private fun init(){

    }
    private fun initListener(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frame_layout) as NavHostFragment
        val navController = navHostFragment.findNavController()

        navController.addOnDestinationChangedListener(){ _, destination, _ ->
            when(destination.id){
                R.id.home_menu -> {
                    binding.bottomNavView.menu.findItem(R.id.home_menu).setIcon(R.drawable.home_filled)
                    binding.bottomNavView.menu.findItem(R.id.reports_menu).setIcon(R.drawable.report_empty)
                    binding.bottomNavView.menu.findItem(R.id.health_menu).setIcon(R.drawable.health_empty)
                    binding.bottomNavView.menu.findItem(R.id.profile_menu).setIcon(R.drawable.profile_empty)
                }
                R.id.reports_menu -> {
                    binding.bottomNavView.menu.findItem(R.id.home_menu).setIcon(R.drawable.home_empty)
                    binding.bottomNavView.menu.findItem(R.id.reports_menu).setIcon(R.drawable.reports_filled)
                    binding.bottomNavView.menu.findItem(R.id.health_menu).setIcon(R.drawable.health_empty)
                    binding.bottomNavView.menu.findItem(R.id.profile_menu).setIcon(R.drawable.profile_empty)
                }
                R.id.health_menu -> {
                    binding.bottomNavView.menu.findItem(R.id.home_menu).setIcon(R.drawable.home_empty)
                    binding.bottomNavView.menu.findItem(R.id.reports_menu).setIcon(R.drawable.report_empty)
                    binding.bottomNavView.menu.findItem(R.id.health_menu).setIcon(R.drawable.health_filled)
                    binding.bottomNavView.menu.findItem(R.id.profile_menu).setIcon(R.drawable.profile_empty)
                }
                R.id.profile_menu -> {
                    binding.bottomNavView.menu.findItem(R.id.home_menu).setIcon(R.drawable.home_empty)
                    binding.bottomNavView.menu.findItem(R.id.reports_menu).setIcon(R.drawable.report_empty)
                    binding.bottomNavView.menu.findItem(R.id.health_menu).setIcon(R.drawable.health_empty)
                    binding.bottomNavView.menu.findItem(R.id.profile_menu).setIcon(R.drawable.profile_filled)
                }
            }
        }

        binding.bottomNavView.setupWithNavController(navController)
    }
}