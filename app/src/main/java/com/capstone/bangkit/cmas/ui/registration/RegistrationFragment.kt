package com.capstone.bangkit.cmas.ui.registration

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.FragmentRegistrationBinding
import com.capstone.bangkit.cmas.ui.home.HomeActivity
import com.capstone.bangkit.cmas.ui.profile.ProfileActivity
import com.capstone.bangkit.cmas.utils.animateVisibility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    lateinit var progressDialog: ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAction()

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Tunggu sebentar...")
        progressDialog.setMessage("Ini mungkin membutuhkan waktu beberapa saat. Harap tunggu.")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setAction() {
//        setLoading(true)
        binding.apply {
            binding.btnDaftar.setOnClickListener {
                val name = binding.edtName.text.toString()
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                val passwordConfirm = binding.edtPasswordConfirm.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()) {
                    if (password == passwordConfirm) {
                        progressDialog.show()
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val userProfile = userProfileChangeRequest {
                                        displayName = name
                                    }
                                    val user = task.result.user
                                    user!!.updateProfile(userProfile)
                                        .addOnCompleteListener {
                                            Intent(
                                                requireContext(),
                                                HomeActivity::class.java
                                            ).also { intent ->
                                                startActivity(intent)
                                            }
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                requireContext(),
                                                R.string.auth_error_message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                }
                            }
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(requireContext(), "Password harus sama", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Harus diisi semua!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            binding.login.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_registrationFragment_to_loginFragment)
            )

        }
//        edtEmail()
        edtPassword()
        edtPasswordConfirm()
    }

    private fun registerProcess() {
        val name = binding.edtName.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userProfile = userProfileChangeRequest {
                        displayName = name
                    }
                    val user = task.result?.user
                    user?.updateProfile(userProfile)?.addOnCompleteListener { updateUserTask ->
                        if (updateUserTask.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Registration successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            Intent(requireContext(), HomeActivity::class.java).also {
                                startActivity(it)
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Failed to update user profile",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    val error = task.exception?.localizedMessage ?: "Registration failed"
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { error ->
                Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun edtPassword() {
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s?.toString() ?: ""
                if (password.length < 8) {
                    binding.txtInputLayoutPassword.error =
                        getString(R.string.password_minimum_character)
                } else {
                    binding.txtInputLayoutPassword.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Nothing
            }

        })
    }

    private fun edtPasswordConfirm() {
        binding.edtPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s?.toString() ?: ""
                if (password.length < 8) {
                    binding.txtInputLayoutPasswordConfirm.error =
                        getString(R.string.password_minimum_character)
                } else {
                    binding.txtInputLayoutPasswordConfirm.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val password = binding.edtPassword.text.toString()
                val passwordConfirm = binding.edtPasswordConfirm.text.toString()

                if (password == passwordConfirm) {
                    // Launch Register
                } else {
                    binding.txtInputLayoutPasswordConfirm.error =
                        getString(R.string.password_confirm_message)
                }
            }

        })
    }

    private fun edtEmail() {
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s?.toString() ?: ""
                if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.txtInputLayoutEmail.error = getString(R.string.email_error_message)
                } else {
                    binding.txtInputLayoutPassword.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Nothing
            }

        })
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            edtEmail.isEnabled = !isLoading
            edtPassword.isEnabled = !isLoading
            edtName.isEnabled = !isLoading
            edtPasswordConfirm.isEnabled = !isLoading

            if (isLoading) {
                viewLoading.animateVisibility(true)
            } else {
                viewLoading.animateVisibility(false)
            }
        }
    }
}