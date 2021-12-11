package com.example.allinone.ui.ui.breakingnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allinone.databinding.FragmentBreakingnewsBinding
import com.example.allinone.ui.adapter.NewsAdapter
import com.example.allinone.ui.ui.launchnews.NewsActivity
import com.example.allinone.ui.ui.launchnews.NewsViewModel
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
    }

    private fun setBreakingNewsObserver() {
        viewModel.breakingNewsLiveData.observe(viewLifecycleOwner, Observer { response ->
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
        _binding?.rvBreakingNews.apply {
            this?.adapter = newsAdapter
            this?.layoutManager = LinearLayoutManager(activity)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}