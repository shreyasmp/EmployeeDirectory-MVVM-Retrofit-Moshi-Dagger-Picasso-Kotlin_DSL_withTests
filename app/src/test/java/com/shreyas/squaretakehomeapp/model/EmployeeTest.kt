package com.shreyas.squaretakehomeapp.model

import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EmployeeTest {

    @Test
    fun copy() {
        val originalResponse = Employee(
            uuid = "someID",
            full_name = "Zack Snyder",
            phone_number = null,
            email_address = "something@square.com",
            biography = null,
            photo_url_small = null,
            photo_url_large = null,
            team = "Director",
            employee_type = Employee.EmployeeType.FULL_TIME
        )
        val copiedResponse = originalResponse.copy()
        Truth.assertThat(copiedResponse).isEqualTo(originalResponse)
    }
}