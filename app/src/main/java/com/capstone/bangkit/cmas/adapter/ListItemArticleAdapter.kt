package com.capstone.bangkit.cmas.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.data.local.entity.Article
import com.capstone.bangkit.cmas.data.remote.response.ArticleResponseItem
import com.capstone.bangkit.cmas.ui.article.DetailArticleActivity

class ListItemArticleAdapter (
    private val listArticle: List<ArticleResponseItem>): RecyclerView.Adapter<ListItemArticleAdapter.ListViewHolder>()
{
    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_article_jdl)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_article_desc)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.article_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val article = listArticle[position]
        holder.tvName.text = article.title
        holder.tvDescription.text = article.description

//        val currentArticle = listArticle[position]
        holder.itemView.setOnClickListener {
            val intentToDetails = Intent(holder.itemView.context, DetailArticleActivity::class.java)
            intentToDetails.putExtra(DetailArticleActivity.KEY_ARTICLE, article)
            holder.itemView.context.startActivity(intentToDetails)
        }
    }

    override fun getItemCount(): Int = listArticle.size


}