package com.capstone.bangkit.cmas.ui.chatbot

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.FragmentChatbotBinding
import com.capstone.bangkit.cmas.databinding.FragmentHomeBinding

class ChatbotFragment : Fragment() {

    private var _binding: FragmentChatbotBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatbotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            button.setOnClickListener {
                Intent(requireContext(), ChatbotActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

}