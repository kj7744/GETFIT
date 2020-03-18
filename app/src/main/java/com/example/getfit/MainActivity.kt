package com.example.getfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.app_bar_main.toolbar

import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.show_more_dialog.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var homeFragment: HomeFragment
    lateinit var Equipmentfragment: EquipmentFragment
    lateinit var GymPackagesfragment: GymPackagesFragment
    lateinit var Fitnessfragment: FitnessFragment
    lateinit var Trainerfragment: TrainerFragment
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "GETFIT"

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            (R.string.open),
            (R.string.close)
        ) {

        }
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        //default fragment is home

        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
           // .addToBackStack(null)
            .commit()


    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.home -> {
                homeFragment = HomeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, homeFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
             //       .addToBackStack(null)
                    .commit()
            }
            R.id.gym_equipment -> {
                Equipmentfragment = EquipmentFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, Equipmentfragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.gym_Packages -> {
                GymPackagesfragment = GymPackagesFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, GymPackagesfragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.trainers -> {
                Trainerfragment = TrainerFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, Trainerfragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.fitness_mercahndise -> {
                Fitnessfragment = FitnessFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, Fitnessfragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.support -> {
                startActivity(Intent(this, Queries::class.java))
            }
            R.id.logout -> {
                auth = FirebaseAuth.getInstance()
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun onRadioButtonClicked(view: View) {}
    fun membership(view: View) {}


}
