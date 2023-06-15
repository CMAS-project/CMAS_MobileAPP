package com.capstone.bangkit.cmas.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.ui.home.HomeActivity
import com.capstone.bangkit.cmas.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            // kalau udah login
            Handler().postDelayed({
                navigateToHomeActivity()
            }, 2000)

        } else {
            // Klo belum login
            Handler().postDelayed({
                navigateToAuthActivity()
            }, 3000)
        }


    }

    private fun navigateToAuthActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}