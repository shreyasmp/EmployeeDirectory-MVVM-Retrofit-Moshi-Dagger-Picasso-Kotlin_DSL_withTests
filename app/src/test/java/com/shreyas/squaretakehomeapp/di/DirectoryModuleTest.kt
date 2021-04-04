package com.shreyas.squaretakehomeapp.di

import com.google.common.truth.Truth.assertThat
import com.shreyas.squaretakehomeapp.MainApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.time.Instant

class DirectoryModuleTest {

    private val mockApplication = mockk<MainApplication>()
    private lateinit var module: DirectoryModule

    @Before
    fun setUp() {
        module = FakeDirectoryModule()
    }

    @Test
    fun `test provide context returns expected application context`() {
        every { mockApplication.applicationContext } returns mockApplication

        val result = DirectoryModule.provideApplicationContext(mockApplication)

        assertThat(result).isEqualTo(mockApplication)
        verify { mockApplication.applicationContext }

        Instant.now()
    }

    class FakeDirectoryModule : DirectoryModule() {

    }
}