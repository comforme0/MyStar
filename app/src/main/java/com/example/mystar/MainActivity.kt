package com.example.mystar

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mystar.navigation.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
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
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(this, AddPhotoActivity::class.java))
                }

                return true
            }
            R.id.action_favoriate_alarm -> {
                var alarmViewFragement = AlarmViewFragement()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, alarmViewFragement).commit()
                return true
            }
            R.id.action_account -> {
                var userViewFragment = UserViewFragment()
                var bundle = Bundle()
                var uid = FirebaseAuth.getInstance().currentUser?.uid
                bundle.putString("destinationUid", uid)
                userViewFragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.main_content, userViewFragment).commit()
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        bottom_navigation.selectedItemId = R.id.action_home
    }
}
