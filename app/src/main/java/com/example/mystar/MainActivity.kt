package com.example.mystar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.mystar.navigation.AlarmViewFragement
import com.example.mystar.navigation.DetailViewFragement
import com.example.mystar.navigation.GridViewFragement
import com.example.mystar.navigation.UserViewFragement
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.action_home -> {
                var detailViewFragement = DetailViewFragement()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, detailViewFragement).commit()
                return true
            }
            R.id.action_search -> {
                var gridFragement = GridViewFragement()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, gridFragement).commit()
                return true
            }
            R.id.action_add_photo -> {
                return true
            }
            R.id.action_favoriate_alarm -> {
                var alarmViewFragement = AlarmViewFragement()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, alarmViewFragement).commit()
                return true
            }
            R.id.action_account -> {
                var userViewFragement = UserViewFragement()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, userViewFragement).commit()
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }
}
