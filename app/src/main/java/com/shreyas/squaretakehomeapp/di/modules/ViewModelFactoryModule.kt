package com.shreyas.squaretakehomeapp.di.modules

import androidx.lifecycle.ViewModelProvider
import com.shreyas.squaretakehomeapp.di.DaggerViewModelFactory
import com.shreyas.squaretakehomeapp.repository.DirectoryRepositoryImpl
import com.shreyas.squaretakehomeapp.repository.DirectoryRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bind(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun providesEmployeeRepository(repository: DirectoryRepositoryImpl): DirectoryRepository
}