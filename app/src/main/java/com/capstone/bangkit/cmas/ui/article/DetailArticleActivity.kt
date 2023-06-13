package com.capstone.bangkit.cmas.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.databinding.ActivityDetailArticleBinding
import com.capstone.bangkit.cmas.data.local.entity.Article

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataArticle = intent.getParcelableExtra<Article>(KEY_ARTICLE)

        if (dataArticle != null) {
            val judul = binding.tvJudul
            val desc = binding.tvDesc

            judul.text = dataArticle.title
            desc.text = dataArticle.description
        }

        customToolbar()

    }

    private fun customToolbar() {
        binding.apply {
            toolbar.navBack.setOnClickListener {
                onBackPressed()
            }
            toolbar.tvToolbarName.setText(R.string.article)
        }
    }

    companion object {
        const val KEY_ARTICLE = "article"
    }
}