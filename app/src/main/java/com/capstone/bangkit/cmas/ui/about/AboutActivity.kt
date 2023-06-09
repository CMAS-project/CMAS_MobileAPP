package com.capstone.bangkit.cmas.ui.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customToolbar()
    }

    private fun customToolbar() {

        binding.apply {
            toolbar.navBack.setOnClickListener() {
                onBackPressed()
            }
            toolbar.tvToolbarName.setText(R.string.about_us)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}