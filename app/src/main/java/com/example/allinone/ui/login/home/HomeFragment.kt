package com.example.allinone.ui.login.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allinone.R
import com.example.allinone.databinding.FragmentHomeBinding
import com.example.allinone.ui.login.data.model.User
import com.example.allinone.ui.utils.Resource
import com.example.allinone.ui.utils.handleApiError
import com.example.allinone.ui.utils.logout
import com.example.allinone.ui.utils.visible
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
//    private val viewModel : HomeViewModel by viewModels<HomeViewModel>()

    lateinit var viewModel : HomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.progressbar.visible(false)

        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)

        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    updateUI(it.data?.user!!)
                }
                is Resource.Loading -> {
                    binding.progressbar.visible(true)
                }
                is Resource.Error -> {
                    handleApiError(it.message!!)
                }
            }
        })

        binding.buttonLogout.setOnClickListener {
            logout()
        }
    }

    private fun updateUI(user: User) {
        with(binding) {
            textViewId.text = user.id.toString()
            textViewName.text = user.name
            textViewEmail.text = user.email
        }
    }
}