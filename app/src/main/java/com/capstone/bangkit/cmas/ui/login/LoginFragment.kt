package com.capstone.bangkit.cmas.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.FragmentLoginBinding
import com.capstone.bangkit.cmas.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: LoginViewModel by viewModels()

    lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Tunggu sebentar...")
        progressDialog.setMessage("Ini mungkin membutuhkan waktu beberapa saat.")

        action()
        observeViewModel()
    }

    private fun action() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                progressDialog.show()
                viewModel.login(email, password)
            } else {
                progressDialog.dismiss()
                Toast.makeText(
                    requireContext(),
                    "Silahkan isi email dan password dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.signUp.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registrationFragment)
        )
        edtPassword()

        binding.ivGoogle.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan ini belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                progressDialog.dismiss()
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                progressDialog.dismiss()
                if (binding.edtEmail.text?.isNotEmpty() == true && binding.edtPassword.text?.isNotEmpty() == true) {
                    Toast.makeText(requireContext(), R.string.auth_error_message, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

}