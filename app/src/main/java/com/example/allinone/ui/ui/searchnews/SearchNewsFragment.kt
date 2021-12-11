package com.example.allinone.ui.ui.searchnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allinone.databinding.FragmentSearchnewsBinding
import com.example.allinone.ui.ui.launchnews.NewsActivity
import com.example.allinone.ui.ui.launchnews.NewsViewModel

class SearchNewsFragment : Fragment() {

//    private lateinit var searchNewsViewModel: SearchNewsViewModel
    private var _binding: FragmentSearchnewsBinding? = null
    lateinit var viewModel: NewsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        searchNewsViewModel =
//            ViewModelProvider(this).get(SearchNewsViewModel::class.java)

        _binding = FragmentSearchnewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textSearchnews
//        searchNewsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}