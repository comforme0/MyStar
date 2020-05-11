package com.example.mystar

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mystar.navigation.*
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        setToolbarDefult()
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

    private fun setToolbarDefult() {
        toolbar_username.visibility = View.GONE
        toolbar_btn_back.visibility = View.GONE
        toolbar_title_image.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        bottom_navigation.selectedItemId = R.id.action_home
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UserViewFragment.PICK_PROFILE_FROM_ALBUM && resultCode == Activity.RESULT_OK) {
            var imageUri = data?.data
            var uid = FirebaseAuth.getInstance().currentUser?.uid
            var storeRef = FirebaseStorage.getInstance().reference.child("userProfileImages").child(uid!!)
            storeRef.putFile(imageUri!!).continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
                return@continueWithTask storeRef.downloadUrl
            }.addOnSuccessListener { uri ->
                var map = HashMap<String, Any>()
                map["image"] = uri.toString()
                FirebaseFirestore.getInstance().collection("profileImages").document(uid).set(map)
            }
        }
    }
}
