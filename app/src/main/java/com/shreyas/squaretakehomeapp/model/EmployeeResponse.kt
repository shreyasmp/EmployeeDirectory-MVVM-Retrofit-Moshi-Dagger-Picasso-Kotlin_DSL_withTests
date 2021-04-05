package com.shreyas.squaretakehomeapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class EmployeeResponse(val employees: MutableList<Employee>) : Serializable {

}
