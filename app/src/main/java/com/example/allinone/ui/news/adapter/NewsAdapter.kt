package com.example.allinone.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.allinone.databinding.ItemArticlePreviewBinding
import com.example.allinone.ui.news.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
//Note: Observe we are not passing data/context in NewsAdapter constructor.
// notifydatasetChanged will be called everytime even data not changed scenarios. We use DiffUtil


    private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url // as we dont have id in response, we are using url which is unique.
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtilCallback)


    inner class ArticleViewHolder(val binding: ItemArticlePreviewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        val binding =  ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article = differ.currentList[position]

        with(holder) {
                Glide.with(binding.root).load(article.urlToImage).into(binding.ivArticleImage)
                binding.tvSource.text = article.source?.name
                binding.tvTitle.text = article.title
                binding.tvDescription.text = article.description
                binding.tvPublishedAt.text = article.publishedAt

                binding.root.setOnClickListener {
                    onItemClickListener?.let {
                        it(article)
                    }
                }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    //On Item click listeners using lambda functions
    private var onItemClickListener : ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener : (Article) -> Unit) {
        onItemClickListener = listener
    }
}