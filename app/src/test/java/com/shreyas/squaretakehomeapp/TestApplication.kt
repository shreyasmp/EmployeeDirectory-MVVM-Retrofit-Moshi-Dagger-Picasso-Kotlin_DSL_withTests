package com.shreyas.squaretakehomeapp

import com.shreyas.squaretakehomeapp.di.DaggerTestComponent_TestAppComponent

open class TestApplication : MainApplication() {

    override fun onCreate() {
        super.onCreate()

        createAppDaggerComponent()
    }

    private fun createAppDaggerComponent() {
        val component = DaggerTestComponent_TestAppComponent.builder().application(this).build()
        component.inject(this)
    }
}