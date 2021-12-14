package com.example.allinone.ui.news.ui.searchnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allinone.R
import com.example.allinone.databinding.FragmentSearchnewsBinding
import com.example.allinone.ui.news.adapter.NewsAdapter
import com.example.allinone.ui.news.ui.launchnews.NewsActivity
import com.example.allinone.ui.news.ui.launchnews.NewsViewModel
import com.example.allinone.ui.utils.Constants
import com.example.allinone.ui.utils.Constants.SEARCH_NEWS_TIME_DELAY
import com.example.allinone.ui.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    //    private lateinit var searchNewsViewModel: SearchNewsViewModel
    private var _binding: FragmentSearchnewsBinding? = null
    lateinit var viewModel: NewsViewModel
    private val TAG = "SearchNewsFragment"
    lateinit var newsAdapter: NewsAdapter
    var job : Job? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var isLoading = false
    var isLastPage = false
    var isScorlling = false
    val newsScorllListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            //To make sure about reaching bottom of recylerView
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBiginning = firstVisibleItemPosition >= 0
            val isTotalMorethanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBiginning && isTotalMorethanVisible && isScorlling

            if (shouldPaginate) {
                viewModel.searchNews(binding.etSearch.text.toString())
                isScorlling = true
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScorlling = true
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchnewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        triggerSearchNewsByQuery()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel


        setSearcnNewsObserver()
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_navigation_searchnews_to_articleFragment,bundle)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setSearcnNewsObserver() {
        viewModel.searchNewsLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressbar()
                    response.data?.let { newsAdapter.differ.submitList(it.articles.toList()) }
                    val totalPages = response.data?.totalResults!!/ Constants.QUERY_PAGE_SIZE + 2
                    isLastPage = viewModel.searchNewsPage == totalPages
                    if(isLastPage) {
                        Log.d(TAG, "Setting padding 0 0 0 0")
                        binding.rvSearchNews.setPadding(0, 0, 0, 0)
                    }

                }
                is Resource.Error -> {
                    hideProgressbar()
                    response.message?.let {
                        Toast.makeText(activity,"No Internent", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error Occured $it") }
                }
                is Resource.Loading -> {
                    showProgressbar()
                }
            }
        })
    }

    private fun showProgressbar() {
        _binding?.paginationProgressBar?.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideProgressbar() {
        _binding?.paginationProgressBar?.visibility = View.GONE
        isLoading = false
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        _binding?.rvSearchNews.apply {
            this?.adapter = newsAdapter
            this?.layoutManager = LinearLayoutManager(activity)
            this?.addOnScrollListener(newsScorllListener)
        }

    }

    private fun triggerSearchNewsByQuery() {
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchNews(it.toString())
                    }
                }

            }
        }
    }


}