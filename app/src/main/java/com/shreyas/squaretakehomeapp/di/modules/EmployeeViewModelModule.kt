package com.shreyas.squaretakehomeapp.di.modules

import androidx.lifecycle.ViewModel
import com.shreyas.squaretakehomeapp.di.annotations.ViewModelKey
import com.shreyas.squaretakehomeapp.viewmodel.EmployeeListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class EmployeeViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EmployeeListViewModel::class)
    abstract fun bindEmployeeListViewModel(employeeListViewModel: EmployeeListViewModel): ViewModel
}