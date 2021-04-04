package com.shreyas.squaretakehomeapp.view

import android.os.SystemClock
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shreyas.squaretakehomeapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmployeeListFragmentAndroidTest {

    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_employee_list_screen_by_scrolling() {
        mActivityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        // Adding delay to ensure the list loads and enough time for actions to perform
        SystemClock.sleep(3000)
        with(onView(withId(R.id.employee_list))) {
            perform(
                RecyclerViewActions.scrollToPosition<EmployeeListAdapter.EmployeeViewHolder>(8)
            )
        }
        // Another delay to scroll up
        SystemClock.sleep(3000)
        with(onView(withId(R.id.employee_list))) {
            perform(
                RecyclerViewActions.scrollToPosition<EmployeeListAdapter.EmployeeViewHolder>(
                    1
                )
            )
        }
    }
}