package com.shreyas.squaretakehomeapp.di

import android.app.Application
import android.content.Context
import com.shreyas.squaretakehomeapp.di.modules.*
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        DirectoryServiceModule::class,
        PicassoModule::class,
        EmployeeViewModule::class,
        EmployeeViewModelModule::class
    ]
)
abstract class DirectoryModule {

    companion object {
        @Provides
        fun provideApplicationContext(application: Application): Context {
            return application.applicationContext
        }
    }
}