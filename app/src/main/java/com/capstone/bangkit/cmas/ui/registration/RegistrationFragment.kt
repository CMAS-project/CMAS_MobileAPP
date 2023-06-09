package com.capstone.bangkit.cmas.ui.registration

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

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
        genderSelection()
    }


    private fun genderSelection() {
        val genderArray = resources.getStringArray(R.array.gender_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, genderArray)

        binding.apply {
            gender.keyListener = null
            gender.setAdapter(adapter)

            gender.setOnItemClickListener { parent, _, position, _ ->
                val selectedGender = parent.getItemAtPosition(position).toString()
                txtInputLayoutGender.editText?.setText(selectedGender)
//                gender.setText("")
//                gender.text = selectedGender
            }
        }
    }

//                true
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setAction() {
        binding.apply {
            login.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_registrationFragment_to_loginFragment)
            )
        }
    }
}