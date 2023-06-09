package com.capstone.bangkit.cmas.ui.scan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.ActivityScanBinding
import com.capstone.bangkit.cmas.ui.ProfileActivity

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.notification -> {
//                Toast.makeText(this@ScanActivity,"Ini Notifikasi mu", Toast.LENGTH_SHORT).show()
//            true
//            }
//            R.id.profile -> {
//                Intent(this, ProfileActivity::class.java).also {intent ->
//                    startActivity(intent)
//                }
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//
//    }
}