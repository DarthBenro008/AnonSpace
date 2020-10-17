package com.benrostudios.anonymouspace.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.benrostudios.anonymouspace.R
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navController = findNavController(R.id.nav_host_fragment_home_activity)
        bottom_navigation_view_home_activity.setBackgroundColor("#121212".toColorInt())
        bottom_navigation_view_home_activity.setupWithNavController(navController)
    }
}