package com.example.allinone.ui.ui.searchnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allinone.R
import com.example.allinone.databinding.FragmentSearchnewsBinding
import com.example.allinone.ui.adapter.NewsAdapter
import com.example.allinone.ui.ui.launchnews.NewsActivity
import com.example.allinone.ui.ui.launchnews.NewsViewModel
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
                    response.data?.let { newsAdapter.differ.submitList(it.articles) }

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
    }

    private fun hideProgressbar() {
        _binding?.paginationProgressBar?.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        _binding?.rvSearchNews.apply {
            this?.adapter = newsAdapter
            this?.layoutManager = LinearLayoutManager(activity)
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