package com.example.allinone.ui.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.allinone.R
import com.example.allinone.databinding.FragmentArticleBinding
import com.example.allinone.ui.ui.launchnews.NewsActivity
import com.example.allinone.ui.ui.launchnews.NewsViewModel


class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel

    // ArticleFragmentArgs is generated class. args will be containing
    // the bundle we are passig to this fragment
    val args: ArticleFragmentArgs by navArgs()
    private var binding : FragmentArticleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater,container,false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        binding?.webView.apply {
            this?.webViewClient = WebViewClient()
            this?.loadUrl(article.url)
        }
        val supportActionBar = (activity as NewsActivity).supportActionBar
        supportActionBar?.title = article.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //This is mandatory to work back icon click
        setHasOptionsMenu(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {
                (activity as NewsActivity).onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}