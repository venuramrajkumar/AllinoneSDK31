package com.example.allinone.ui.di.modules



import com.example.allinone.ui.login.ui.AuthActivity
import com.example.allinone.ui.login.ui.LoginFragment
import com.example.allinone.ui.login.ui.RegisterFragment
import com.raj.mycarride.di.modules.NetworkModule
import com.raj.mycarride.di.modules.RetrofitModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module (includes = [RetrofitModule::class, NetworkModule::class, ViewModelModule::class])
abstract class ActivityFragmentBindingModule {


    @ContributesAndroidInjector
    abstract fun bindAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun bindLoginFragment() : LoginFragment

    @ContributesAndroidInjector
    abstract fun bindRegisterFragment() : RegisterFragment
}