package com.shreyas.squaretakehomeapp.di

import android.app.Application
import com.shreyas.squaretakehomeapp.TestApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface TestComponent {

    @Singleton
    @Component(
        modules = [
            DirectoryModule::class
        ]
    )
    interface TestAppComponent {

        fun inject(mainApplication: TestApplication)

        @Component.Builder
        interface Builder {

            fun build(): TestAppComponent

            @BindsInstance
            fun application(application: Application): Builder
        }
    }
}