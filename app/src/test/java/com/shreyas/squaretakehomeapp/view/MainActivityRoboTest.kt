package com.shreyas.squaretakehomeapp.view

import android.os.Build
import android.os.Looper
import android.view.View
import com.google.common.truth.Truth.assertThat
import com.shreyas.squaretakehomeapp.R
import com.shreyas.squaretakehomeapp.runner.DirectoryRobolectricTestRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(DirectoryRobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@LooperMode(LooperMode.Mode.LEGACY)
class MainActivityRoboTest {

    private lateinit var activityTest: MainActivity

    @Before
    fun setUp() {
        activityTest =
            Robolectric.buildActivity(MainActivity::class.java).create().visible().get()
        Shadows.shadowOf(Looper.getMainLooper()).idle()
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
}