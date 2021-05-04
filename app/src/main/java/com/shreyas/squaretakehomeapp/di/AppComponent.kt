package com.shreyas.squaretakehomeapp.di

import android.app.Application
import com.shreyas.squaretakehomeapp.MainApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DirectoryModule::class
    ]
)
interface AppComponent {

    fun inject(mainApplication: MainApplication)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}