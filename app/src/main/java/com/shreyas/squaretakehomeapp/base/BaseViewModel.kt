package com.shreyas.squaretakehomeapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    companion object {
        private val TAG = BaseViewModel::class.java.simpleName
    }

    val isError: MutableLiveData<Boolean> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        isError.postValue(false)
        isLoading.postValue(false)
    }
}