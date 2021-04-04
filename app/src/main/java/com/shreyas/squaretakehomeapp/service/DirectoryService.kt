package com.shreyas.squaretakehomeapp.service

import com.shreyas.squaretakehomeapp.model.EmployeeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface DirectoryService {

    @Headers("Content-Type: application/json")
    @GET(value = "employees.json")
    suspend fun fetchEmployeeDirectory(): Response<EmployeeResponse>

    // Other scenarios to test for service returning different responses
    @Headers("Content-Type: application/json")
    @GET(value = "employees_malformed.json")
    suspend fun fetchEmployeeMalformedDirectory(): Response<EmployeeResponse>

    @Headers("Content-Type: application/json")
    @GET(value = "employees_empty.json")
    suspend fun fetchEmployeeEmptyDirectory(): Response<EmployeeResponse>
}