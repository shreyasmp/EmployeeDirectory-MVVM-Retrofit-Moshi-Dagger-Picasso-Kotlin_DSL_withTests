package com.shreyas.squaretakehomeapp.view

import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import com.google.common.truth.Truth.assertThat
import com.shreyas.squaretakehomeapp.R
import com.shreyas.squaretakehomeapp.runner.DirectoryRobolectricTestRunner
import com.shreyas.squaretakehomeapp.utils.TestJsonUtils.startFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode

@RunWith(DirectoryRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class EmployeeListFragmentRoboTest {

    private lateinit var employeeListFragment: EmployeeListFragment
    private lateinit var navHostFragment: NavHostFragment

    @Before
    fun setUp() {
        employeeListFragment = EmployeeListFragment()
        navHostFragment = NavHostFragment()
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun `test if employee list fragment is created`() {
        startFragment(employeeListFragment)
        assertThat(employeeListFragment).isNotNull()
    }

    @Test
    fun `test if the first fragment is visible`() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity ->
            activity.supportFragmentManager.let { fragmentManager ->
                fragmentManager.executePendingTransactions()
                val targetFragment = fragmentManager.findFragmentById(R.id.fragment_container)
                assertThat(targetFragment?.isVisible).isTrue()
            }
        }
    }
}