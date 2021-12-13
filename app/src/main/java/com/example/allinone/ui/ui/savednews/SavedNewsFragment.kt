package com.example.allinone.ui.ui.savednews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allinone.R
import com.example.allinone.databinding.FragmentSavednewsBinding
import com.example.allinone.ui.adapter.NewsAdapter
import com.example.allinone.ui.ui.launchnews.NewsActivity
import com.example.allinone.ui.ui.launchnews.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment() {

//    private lateinit var savedNewsViewModel: SavedNewsViewModel
    private var _binding: FragmentSavednewsBinding? = null
    lateinit var viewModel : NewsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        savedNewsViewModel =
//            ViewModelProvider(this).get(SavedNewsViewModel::class.java)

        _binding = FragmentSavednewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_navigation_savednews_to_articleFragment,bundle)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
            articles -> newsAdapter.differ.submitList(articles)
        })

        setupItemTouchHelper(view)
    }

    private fun setupItemTouchHelper(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemtoDelete = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(itemtoDelete)

                Snackbar.make(view, "Article Delete Successfully", Snackbar.LENGTH_SHORT).apply {

                    setAction("Undo") {
                        viewModel.saveAndUpdateArticles(itemtoDelete)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvSavedNews)
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        _binding?.rvSavedNews.apply {
            this?.adapter = newsAdapter
            this?.layoutManager = LinearLayoutManager(activity)
        }

    }
}