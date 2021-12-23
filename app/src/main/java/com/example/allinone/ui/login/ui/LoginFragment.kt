package com.example.allinone.ui.login.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allinone.databinding.FragmentLoginBinding
import com.example.allinone.ui.login.base.BaseFragment
import com.example.allinone.ui.login.data.UserPreferences
import com.example.allinone.ui.login.data.model.LoginResponse
import com.example.allinone.ui.login.viewmodel.LoginViewModel
import com.example.allinone.ui.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var userPreferences: UserPreferences

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        setObservers(viewModel)

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            viewModel.makeLogin(email, password)
        }



        binding.textViewRegisterNow.setOnClickListener {
            var accessToken: String? = null
            userPreferences.accessToken.
            subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    accessToken = it
                    Toast.makeText(activity, accessToken ,Toast.LENGTH_SHORT).show()
                }
            //findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }

    }

    private fun setObservers(viewModel: LoginViewModel) {
        val loginObserver = Observer<Resource<LoginResponse>> {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(
                        activity,
                        "Login Result ${it.data?.user.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                    userPreferences.saveAuthToken(it.data?.user?.access_token.toString())
                }

                is Resource.Error -> {
                    Toast.makeText(
                        activity,
                        "Login Failure ${it?.message.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

        }
        viewModel.loginResponseLiveData.removeObserver(loginObserver)
        viewModel.loginResponseLiveData.observe(viewLifecycleOwner, loginObserver)

    }

}