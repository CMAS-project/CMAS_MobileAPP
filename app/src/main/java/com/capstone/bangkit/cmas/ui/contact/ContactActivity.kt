package com.capstone.bangkit.cmas.ui.contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customToolbar()
    }

    private fun customToolbar() {

        binding.apply {
            toolbar.navBack.setOnClickListener() {
                onBackPressed()
            }
            toolbar.tvToolbarName.setText(R.string.contact_us)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}