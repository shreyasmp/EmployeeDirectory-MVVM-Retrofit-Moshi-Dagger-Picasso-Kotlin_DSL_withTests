package com.shreyas.squaretakehomeapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Employee(
    val uuid: String,
    val full_name: String, // Required
    val phone_number: String? = null, // Optional
    val email_address: String,
    val biography: String? = null,
    val photo_url_small: String? = null,
    val photo_url_large: String? = null,
    val team: String,
    val employee_type: EmployeeType
) : Serializable {

    enum class EmployeeType {
        FULL_TIME,
        PART_TIME,
        CONTRACTOR
    }
}
