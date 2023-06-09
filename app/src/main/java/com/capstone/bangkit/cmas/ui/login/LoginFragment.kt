package com.capstone.bangkit.cmas.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.FragmentLoginBinding
import com.capstone.bangkit.cmas.ui.home.HomeActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setAction() {
        binding.apply {
            signUp.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registrationFragment)
            )

            btnLogin.setOnClickListener {
                Intent(requireContext(), HomeActivity::class.java).also { intent ->
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }

}