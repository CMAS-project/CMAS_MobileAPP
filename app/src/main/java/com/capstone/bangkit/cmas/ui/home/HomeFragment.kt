package com.capstone.bangkit.cmas.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.adapter.ListItemArticleAdapter
import com.capstone.bangkit.cmas.databinding.FragmentHomeBinding
import com.capstone.bangkit.cmas.data.local.entity.Article
import com.capstone.bangkit.cmas.ui.chatbot.ChatbotFragment
import com.capstone.bangkit.cmas.ui.location.MapsFragment
import com.capstone.bangkit.cmas.ui.profile.ProfileActivity
import com.capstone.bangkit.cmas.ui.article.ArticleFragment
import com.capstone.bangkit.cmas.ui.scan.ScanActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvArticle: RecyclerView
    private val list = ArrayList<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val rootView = binding.root

        rvArticle = binding.rvArticle
        rvArticle.setHasFixedSize(true)

        list.addAll(getListArticle())
        showRecyclerView()

        return rootView
    }

    private fun showRecyclerView() {
        rvArticle.layoutManager = LinearLayoutManager(requireContext())
        val listArticle = ListItemArticleAdapter(list)
        rvArticle.adapter = listArticle
    }

    private fun getListArticle(): Collection<Article> {
        val id = resources.obtainTypedArray(R.array.id)
        val dataName = resources.getStringArray(R.array.data_judul)
        val dataDescription = resources.getStringArray(R.array.data_pengertian)

        val listArticle = ArrayList<Article>()
        val limit = minOf(dataName.size, 3)

        for (i in 0 until limit) {
            val article = Article(id.getResourceId(i, 0), dataName[i], dataDescription[i])
            listArticle.add(article)
        }

        return listArticle
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
                val article = ArticleFragment()
                replaceFragment(article)
            }
            scan.setOnClickListener {
                Intent(requireContext(), ScanActivity::class.java).also {
                    startActivity(it)
                }
            }
            seeMore.setOnClickListener {
                val article = ArticleFragment()
                replaceFragment(article)
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