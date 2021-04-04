package com.shreyas.squaretakehomeapp.view

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.shreyas.squaretakehomeapp.R
import com.shreyas.squaretakehomeapp.TestApplication
import com.shreyas.squaretakehomeapp.di.annotations.ViewModelKey
import com.shreyas.squaretakehomeapp.di.modules.DirectoryServiceModule
import com.shreyas.squaretakehomeapp.di.modules.PicassoModule
import com.shreyas.squaretakehomeapp.di.modules.ViewModelFactoryModule
import com.shreyas.squaretakehomeapp.runner.DirectoryRobolectricTestRunner
import com.shreyas.squaretakehomeapp.viewmodel.EmployeeListViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import dagger.multibindings.IntoMap
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.LooperMode
import javax.inject.Singleton

@RunWith(DirectoryRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.LEGACY)
class MainActivityRoboTest {

    private lateinit var activityTest: MainActivity

    @Before
    fun setUp() {
        activityTest =
            Robolectric.buildActivity(MainActivity::class.java).create().visible().get()
    }

    @Test
    fun `activity is successfully created`() {
        assertThat(activityTest).isNotNull()
    }

    @Test
    fun `should have correct app name`() {
        assertThat(activityTest.getString(R.string.app_name)).isEqualTo("SquareTakeHomeApp")
    }

    @Test
    fun `test activity view components are visible`() {
        assertThat(activityTest.binding.fragmentContainer.visibility).isEqualTo(View.VISIBLE)
        assertThat(activityTest.binding.mainLayout.visibility).isEqualTo(View.VISIBLE)
        assertThat(activityTest.binding.mainToolbar.visibility).isEqualTo(View.VISIBLE)
    }

    @Singleton
    @Component(modules = [TestAppModule::class])
    interface TestAppComponent {
        fun inject(testApplication: TestApplication)
    }

    @Module(
        includes = [
            AndroidSupportInjectionModule::class,
            AndroidInjectionModule::class,
            AndroidSupportInjectionModule::class,
            ViewModelFactoryModule::class,
            DirectoryServiceModule::class,
            PicassoModule::class,
            TestEmployeeViewModelModule::class
        ]
    )
    class TestAppModule {

        @Provides
        fun providesTestApplication(): Application =
            ApplicationProvider.getApplicationContext<TestApplication>()

        @Provides
        fun provideApplicationContext(application: Application): Context {
            return application.applicationContext
        }
    }

    @Module
    abstract class TestEmployeeViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(EmployeeListViewModel::class)
        abstract fun bindEmployeeListViewModel(employeeListViewModel: EmployeeListViewModel): ViewModel
    }
}