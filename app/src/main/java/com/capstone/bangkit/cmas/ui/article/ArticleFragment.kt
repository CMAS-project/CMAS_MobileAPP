package com.capstone.bangkit.cmas.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.adapter.ListItemArticleAdapter
import com.capstone.bangkit.cmas.databinding.FragmentArticleBinding
import com.capstone.bangkit.cmas.data.local.entity.Article

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvArticle: RecyclerView
    private val list = ArrayList<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val rootView = binding.root

        rvArticle = binding.rvArticle
        rvArticle.setHasFixedSize(true)

        list.addAll(getListArticle())
        showRecyclerView()

        customToolbar()

        return rootView
    }

    private fun customToolbar() {
        binding.apply {
            toolbar.navBack.isInvisible = true
            toolbar.tvToolbarName.setText(R.string.article)
        }
    }

    private fun getListArticle(): Collection<Article> {
        val id = resources.obtainTypedArray(R.array.id)
        val dataName = resources.getStringArray(R.array.data_judul)
        val dataDescription = resources.getStringArray(R.array.data_pengertian)

        val listArticle = ArrayList<Article>()

        for (i in dataName.indices) {
            val article = Article(id.getResourceId(i, 0), dataName[i], dataDescription[i])
            listArticle.add(article)
        }

        return listArticle
    }

    private fun showRecyclerView() {
        rvArticle.layoutManager = LinearLayoutManager(requireContext())
        val listArticle = ListItemArticleAdapter(list)
        rvArticle.adapter = listArticle
    }


}