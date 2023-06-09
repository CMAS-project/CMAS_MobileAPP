package com.capstone.bangkit.cmas.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.ActivityProfileBinding
import com.capstone.bangkit.cmas.ui.about.AboutActivity
import com.capstone.bangkit.cmas.ui.contact.ContactActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customToolbar()
        cmasAction()
    }

    private fun cmasAction() {
        binding.apply {
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