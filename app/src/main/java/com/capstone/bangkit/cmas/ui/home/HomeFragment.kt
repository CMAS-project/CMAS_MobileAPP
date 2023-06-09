package com.capstone.bangkit.cmas.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.bangkit.cmas.databinding.FragmentHomeBinding
import com.capstone.bangkit.cmas.ui.profile.ProfileActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarAction()
    }

    private fun toolbarAction() {
        binding.apply {
            toolbar.profile.setOnClickListener {
                Intent(requireContext(), ProfileActivity::class.java).also {
                    startActivity(it)
                }
            }

            toolbar.notification.setOnClickListener {
                Toast.makeText(requireContext(), "Kamu dapat notifikasi", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}