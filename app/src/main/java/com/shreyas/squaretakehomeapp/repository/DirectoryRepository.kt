package com.shreyas.squaretakehomeapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shreyas.squaretakehomeapp.model.EmployeeResponse
import com.shreyas.squaretakehomeapp.service.DirectoryService
import com.shreyas.squaretakehomeapp.utils.ResultWrapper
import javax.inject.Inject
import javax.inject.Singleton

interface DirectoryRepository {
    suspend fun getEmployeeDirectory(): ResultWrapper<LiveData<EmployeeResponse>>
}

@Singleton
open class DirectoryRepositoryImpl @Inject constructor(
    private val service: DirectoryService
) : DirectoryRepository {

    companion object {
        private val TAG = DirectoryRepositoryImpl::class.java.simpleName
    }

    override suspend fun getEmployeeDirectory(): ResultWrapper<LiveData<EmployeeResponse>> {
        val employeeResponse = MutableLiveData<EmployeeResponse>()
        return try {
            // Switch service here to see different service scenarios handled
//            val deferredResponse = service.fetchEmployeeEmptyDirectory()

//            val deferredResponse = service.fetchEmployeeMalformedDirectory()

            val deferredResponse = service.fetchEmployeeDirectoryAsync()
            val responseBody = deferredResponse.await()
            if (deferredResponse.isCompleted && responseBody.body() != null) {
                employeeResponse.postValue(responseBody.body())
                ResultWrapper.SUCCESS(employeeResponse)
            } else {
                ResultWrapper.FAILURE(null)
            }
        } catch (exception: Exception) {
            Log.d(TAG, "Exception: ${exception.message}")
            ResultWrapper.FAILURE(null)
        }
    }
}