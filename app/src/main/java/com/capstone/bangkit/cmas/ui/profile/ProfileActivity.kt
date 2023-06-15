package com.capstone.bangkit.cmas.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.ActivityProfileBinding
import com.capstone.bangkit.cmas.ui.about.AboutActivity
import com.capstone.bangkit.cmas.ui.contact.ContactActivity
import com.capstone.bangkit.cmas.ui.login.LoginViewModel
import com.capstone.bangkit.cmas.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        customToolbar()
        cmasAction()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (!isLoggedIn) {
                // Pengguna telah logout, kembali ke MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun cmasAction() {
        binding.apply {

            // Firebase User
           val name = binding.name

            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                val displayName = firebaseUser.displayName
                if (displayName != null) {
                    name.text = displayName.toUpperCase()
                } else {
                    name.text = "Nama tidak tersedia"
                }
            } else {
                startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
                finish()
            }

            gender.setOnClickListener {
                showGenderDialog()
            }


            logout.setOnClickListener {
                viewModel.logout()
                firebaseAuth.signOut()
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

    private fun updateGender(gender: String) {
        val firebaseUser = firebaseAuth.currentUser
        val userRef = firebaseUser?.uid?.let {
            FirebaseDatabase.getInstance().reference.child("users").child(it)
        }

        // Update field gender dalam database Firebase
        userRef?.child("gender")?.setValue(gender)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Update tampilan CardView dengan jenis kelamin yang dipilih
                    binding.gender.text = gender
                    Toast.makeText(
                        this@ProfileActivity,
                        "Jenis kelamin berhasil diubah",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Gagal mengubah jenis kelamin",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    private fun showGenderDialog() {
        val genderOptions = arrayOf("Laki-laki", "Perempuan")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih Jenis Kelamin")
            .setItems(genderOptions) { _, which ->
                val selectedGender = genderOptions[which]
                updateGender(selectedGender)
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}