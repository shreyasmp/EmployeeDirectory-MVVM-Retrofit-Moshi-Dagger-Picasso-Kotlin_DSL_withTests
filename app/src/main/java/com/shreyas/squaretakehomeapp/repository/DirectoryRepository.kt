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
        val deferredResponse = service.fetchEmployeeDirectory()
        val responseBody = deferredResponse.body()
        return try {
            if (deferredResponse.isSuccessful && responseBody != null) {
                employeeResponse.postValue(deferredResponse.body())
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