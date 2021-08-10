package com.github.cesar1287.class1dhfinalproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    //MutableLiveData - mutável(Permite alterações)
    //LiveData - imutável(read only)
    private val _test: MutableLiveData<String> = MutableLiveData()

    val test: LiveData<String>
        get() = _test
//
//    fun setTest(test: Test) = _test.postValue(test)
}