package com.example.allinone.ui.login.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.allinone.R
import com.example.allinone.databinding.FragmentLoginBinding
import com.example.allinone.ui.login.base.BaseFragment
import com.example.allinone.ui.login.model.LoginResponse
import com.example.allinone.ui.login.viewmodel.LoginViewModel
import com.example.allinone.ui.utils.Resource
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)

        setObservers(viewModel)

        binding.buttonLogin.setOnClickListener {
            val email =  binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            viewModel.makeLogin(email,password)
        }

        binding.textViewRegisterNow.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun setObservers(viewModel: LoginViewModel) {
        val loginObserver =  Observer<Resource<LoginResponse>> {
            Toast.makeText(activity, "Login Result ${it?.data?.user.toString()}", Toast.LENGTH_LONG).show()

        }
        viewModel.loginResponseLiveData.removeObserver(loginObserver)
        viewModel.loginResponseLiveData.observe(viewLifecycleOwner,loginObserver)

    }

}