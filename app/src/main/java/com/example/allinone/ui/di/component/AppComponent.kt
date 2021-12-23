package com.example.allinone.ui.di.component

import android.app.Application
import com.example.allinone.ui.NewsApplication
import com.example.allinone.ui.di.modules.ActivityFragmentBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivityFragmentBindingModule::class,AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<NewsApplication>  {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application) :AppComponent
    }

    // inject(fragmnet), is not required as we are using @ContributeAndroidInjector and AndroidInjector

}