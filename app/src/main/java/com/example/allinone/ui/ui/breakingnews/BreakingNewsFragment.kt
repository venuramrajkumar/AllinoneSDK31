package com.example.allinone.ui.ui.breakingnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allinone.R
import com.example.allinone.databinding.FragmentBreakingnewsBinding
import com.example.allinone.ui.adapter.NewsAdapter
import com.example.allinone.ui.ui.launchnews.NewsActivity
import com.example.allinone.ui.ui.launchnews.NewsViewModel
import com.example.allinone.ui.utils.Constants.QUERY_PAGE_SIZE
import com.example.allinone.ui.utils.Resource


class BreakingNewsFragment : Fragment() {

//    private lateinit var breakingNewsViewModel: BreakingNewsViewModel
    private var _binding: FragmentBreakingnewsBinding? = null
    lateinit var newsAdapter: NewsAdapter
    private val TAG = "BreakingNewsFragment"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var viewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*breakingNewsViewModel =
            ViewModelProvider(this).get(BreakingNewsViewModel::class.java)*/

        _binding = FragmentBreakingnewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        setupRecyclerView()
        setBreakingNewsObserver()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_navigation_breakingnews_to_articleFragment,bundle)
        }


    }

    private fun setBreakingNewsObserver() {
        viewModel.breakingNewsLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressbar()
                    response.data?.let { newsAdapter.differ.submitList(it.articles.toList()) }
                    val totalPages = response.data?.totalResults!!/ QUERY_PAGE_SIZE + 2
                    isLastPage = viewModel.breakingNewsPage == totalPages

                    if (isLastPage) {
                        Log.d(TAG,"Setting padding 0 0 0 0")
                        binding.rvBreakingNews.setPadding(0,0,0,0)
                    }

                }
                is Resource.Error -> {
                    hideProgressbar()
                    response.message?.let { Log.e(TAG, "Error Occured $it") }
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
        _binding?.rvBreakingNews.apply {
            this?.adapter = newsAdapter
            this?.layoutManager = LinearLayoutManager(activity)
            this?.addOnScrollListener(newsScorllListener)
        }

    }

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
            val isTotalMorethanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBiginning && isTotalMorethanVisible && isScorlling

            if (shouldPaginate) {
                viewModel.getBreakingNews("us")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}