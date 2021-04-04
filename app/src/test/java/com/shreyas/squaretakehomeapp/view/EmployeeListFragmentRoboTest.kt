package com.shreyas.squaretakehomeapp.view

import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import com.google.common.truth.Truth.assertThat
import com.shreyas.squaretakehomeapp.R
import com.shreyas.squaretakehomeapp.runner.DirectoryRobolectricTestRunner
import com.shreyas.squaretakehomeapp.utils.TestJsonUtils.startFragment
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode

@RunWith(DirectoryRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.LEGACY)
class EmployeeListFragmentRoboTest {

    private lateinit var employeeListFragment: EmployeeListFragment

    @Before
    fun setUp() {
        employeeListFragment = EmployeeListFragment()
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun `test if employee list fragment is created`() {
        startFragment(employeeListFragment)
        assertThat(employeeListFragment).isNotNull()
    }

    @Ignore
    @Test
    fun `show employee list fragment on launch`() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            activity.supportFragmentManager.let { fragmentManager ->
                fragmentManager.executePendingTransactions()
                val targetFragment = fragmentManager.findFragmentById(R.id.fragment_container)
                assertThat(targetFragment).isEqualTo(employeeListFragment)
            }
        }
    }
}