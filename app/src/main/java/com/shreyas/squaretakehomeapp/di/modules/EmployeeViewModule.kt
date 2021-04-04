package com.shreyas.squaretakehomeapp.di.modules

import com.shreyas.squaretakehomeapp.di.annotations.ActivityScope
import com.shreyas.squaretakehomeapp.view.EmployeeListFragment
import com.shreyas.squaretakehomeapp.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EmployeeViewModule {

    // Activities here
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    // Fragments here
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesEmployeeListFragment(): EmployeeListFragment
}