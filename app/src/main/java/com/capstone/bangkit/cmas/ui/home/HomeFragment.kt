package com.capstone.bangkit.cmas.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.FragmentHomeBinding
import com.capstone.bangkit.cmas.ui.chatbot.ChatbotFragment
import com.capstone.bangkit.cmas.ui.location.MapsFragment
import com.capstone.bangkit.cmas.ui.profile.ProfileActivity
import com.capstone.bangkit.cmas.ui.reflection.ReflectionFragment
import com.capstone.bangkit.cmas.ui.scan.ScanActivity

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
        menuAction()
    }

    private fun menuAction() {
        binding.apply {
            cekLokasi.setOnClickListener {
                val location = MapsFragment()
                replaceFragment(location)
            }
            chatbot.setOnClickListener {
                val chatbot = ChatbotFragment()
                replaceFragment(chatbot)
            }
            article.setOnClickListener {
                val article = ReflectionFragment()
                replaceFragment(article)
            }
            scan.setOnClickListener {
                Intent(requireContext(), ScanActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun toolbarAction() {
        binding.apply {
            toolbar.profile.setOnClickListener {
                Intent(requireContext(), ProfileActivity::class.java).also {
                    startActivity(it)
                }
            }

            toolbar.notification.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}