package com.capstone.bangkit.cmas.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.ActivityProfileBinding
import com.capstone.bangkit.cmas.ui.about.AboutActivity
import com.capstone.bangkit.cmas.ui.contact.ContactActivity
import com.capstone.bangkit.cmas.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        customToolbar()
        cmasAction()
    }

    private fun cmasAction() {
        binding.apply {

            // Firebase User
           val name = binding.name

            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                name.text = firebaseUser.displayName
            } else {
                startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
                finish()
            }

            logout.setOnClickListener {
                Intent(this@ProfileActivity, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }

            aboutUs.setOnClickListener {
                Intent(this@ProfileActivity, AboutActivity::class.java).also {
                    startActivity(it)
                }
            }

            contactUs.setOnClickListener {
                Intent(this@ProfileActivity, ContactActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun customToolbar() {

        binding.apply {
            toolbar.navBack.setOnClickListener() {
                onBackPressed()
            }
            toolbar.tvToolbarName.setText(R.string.profile)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}