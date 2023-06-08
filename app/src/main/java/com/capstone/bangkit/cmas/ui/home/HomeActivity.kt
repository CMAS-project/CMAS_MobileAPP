package com.capstone.bangkit.cmas.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.ActivityHomeBinding
import com.capstone.bangkit.cmas.ui.chatbot.ChatbotFragment
import com.capstone.bangkit.cmas.ui.location.MapsFragment
import com.capstone.bangkit.cmas.ui.reflection.ReflectionFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.background = null
        replaceFragment(HomeFragment())

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.location -> replaceFragment(MapsFragment())
                R.id.chatbot -> replaceFragment(ChatbotFragment())
                R.id.reflection -> replaceFragment(ReflectionFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}