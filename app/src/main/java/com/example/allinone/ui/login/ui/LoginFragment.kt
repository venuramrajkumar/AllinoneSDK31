package com.example.allinone.ui.login.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.allinone.databinding.FragmentLoginBinding
import com.example.allinone.ui.login.base.BaseFragment
import com.example.allinone.ui.login.data.model.LoginResponse
import com.example.allinone.ui.login.home.HomeActivity
import com.example.allinone.ui.login.viewmodel.LoginViewModel
import com.example.allinone.ui.utils.Resource
import com.example.allinone.ui.utils.enable
import com.example.allinone.ui.utils.startNewActivity
import com.example.allinone.ui.utils.visible
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.progressbar.visible(false)
        setObservers(viewModel)

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            binding.progressbar.visible(true)
            binding.buttonLogin.enable(false)
            viewModel.makeLogin(email, password)
        }

        binding.editTextTextPassword.addTextChangedListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.textViewRegisterNow.setOnClickListener {
            //findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }

    }

    private fun setObservers(viewModel: LoginViewModel) {
        val loginObserver = Observer<Resource<LoginResponse>> {
            binding.progressbar.visible(false)
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(
                        requireActivity(),
                        "Login Result ${it.data?.user.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(
                            it.data?.user?.access_token.toString(),
                            it.data?.user?.refresh_token.toString()
                        )
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }

                }

                is Resource.Error -> {
                    Toast.makeText(
                        requireActivity(),
                        "Login Failure ${it?.message.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.buttonLogin.enable(false)
                }

            }

        }
        viewModel.loginResponseLiveData.removeObserver(loginObserver)
        viewModel.loginResponseLiveData.observe(viewLifecycleOwner, loginObserver)

    }

}