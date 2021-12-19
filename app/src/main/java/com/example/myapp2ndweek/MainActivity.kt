package com.example.myapp2ndweek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.myapp2ndweek.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.slider.Slider


class MainActivity : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private var binding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding?.topAppBar)

        supportFragmentManager.beginTransaction().replace(R.id.content, Home()).commit()

//Add binding for bottom Navigation
        binding?.bottomNav?.setOnNavigationItemSelectedListener(this)
        binding?.bottomNav?.selectedItemId = R.id.homeItemBottomNav

//Add binding for card
        binding?.card?.setOnLongClickListener {
            binding?.card?.isChecked = !binding?.card?.isChecked!!
            true
        }

        binding?.buttonMovie?.setOnClickListener(this)

//Add binding for top Application Bar
        binding?.topAppBar?.setOnMenuItemClickListener { menuItem:MenuItem ->

            when(menuItem.itemId) {
                R.id.favoritesItemTopNav -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, Favorites()).commit()
                    true
                }

                R.id.settingsItemTopNav -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, Settings()).commit()
                    true
                }
                else -> false
            }
        }

//Add slider
        binding?.slider?.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                binding?.selectedPart?.text = getString(R.string.action)
            }


            override fun onStopTrackingTouch(slider: Slider) {
                binding?.selectedPart?.text = getString(R.string.beforeAction)
            }
        })

        binding?.slider?.addOnChangeListener { slider, value, fromUser ->
            binding?.descSelectedPart?.text = getString(R.string.yourSelected) + " " + value + " " +
                    getString(R.string.series)
        }
    }




//Methods for bottom Navigation
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.homeItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, Home()).commit()
            R.id.shopItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, Shop()).commit()
            R.id.deliveryItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, Delivery()).commit()
            R.id.accountItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, Account()).commit()
        }

        return true
    }

//Methods for top Application Bar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.top_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {

                val mainMenu = MainMenu()
                mainMenu.show(
                    supportFragmentManager,
                    "main_menu"
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

//Methods for card
    override fun onClick(view: View) {

        val details = Details()

        val parameters = Bundle()

        parameters.putString("nameMovie", binding?.nameMovie?.text?.toString())
        parameters.putString("longMovie", binding?.longMovie?.text?.toString())
        parameters.putString("actorsMovie", binding?.actorsMovie?.text?.toString())

        details.arguments = parameters

        details.show(supportFragmentManager, "details")

    }


}