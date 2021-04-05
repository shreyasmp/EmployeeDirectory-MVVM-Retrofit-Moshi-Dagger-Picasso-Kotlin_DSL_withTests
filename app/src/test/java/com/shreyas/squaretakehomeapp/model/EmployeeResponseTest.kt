package com.shreyas.squaretakehomeapp.model

import com.google.common.truth.Truth.assertThat
import com.shreyas.squaretakehomeapp.utils.TestJsonUtils.getObjectFromJsonFile
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EmployeeResponseTest {
    
    @Test
    fun copy() {
        val employeeResponse =
            getObjectFromJsonFile(jsonFile = "employee_list.json", EmployeeResponse::class.java)
        val originalResponse = employeeResponse?.employees?.let { EmployeeResponse(it) }
        val copiedEmployeeResponse = employeeResponse?.let { originalResponse?.copy(it.employees) }
        assertThat(copiedEmployeeResponse).isEqualTo(originalResponse)
    }
}