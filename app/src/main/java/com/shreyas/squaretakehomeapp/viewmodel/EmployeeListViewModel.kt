package com.shreyas.squaretakehomeapp.viewmodel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shreyas.squaretakehomeapp.base.BaseViewModel
import com.shreyas.squaretakehomeapp.model.Employee
import com.shreyas.squaretakehomeapp.repository.DirectoryRepositoryImpl
import com.shreyas.squaretakehomeapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmployeeListViewModel @Inject constructor(private val repository: DirectoryRepositoryImpl) :
    BaseViewModel() {

    companion object {
        private val TAG = EmployeeListViewModel::class.java.simpleName
    }

    @VisibleForTesting
    internal val _employeeList: MutableLiveData<MutableList<Employee>> = MutableLiveData()
    val employeeList: LiveData<MutableList<Employee>> = _employeeList

    init {
        fetchEmployeeList()
    }

    @VisibleForTesting
    internal fun fetchEmployeeList() {
        isLoading.value = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getEmployeeDirectory()
            }
            when (result) {
                is ResultWrapper.SUCCESS -> {
                    isLoading.value = false
                    val empList = result.value.value?.employees
                    if (empList != null) {
                        if (empList.isNotEmpty()) {
                            isError.value = false
                            Log.i(TAG, "Employee List: $empList")
                            _employeeList.value = empList
                        } else if (empList.isEmpty()) {
                            isError.value = true
                        }
                    }
                }
                is ResultWrapper.FAILURE -> {
                    isLoading.value = false
                    isError.value = true
                    _employeeList.value?.clear()
                }
            }
        }
    }
}