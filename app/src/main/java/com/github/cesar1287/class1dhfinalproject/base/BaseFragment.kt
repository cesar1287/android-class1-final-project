package com.github.cesar1287.class1dhfinalproject.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.github.cesar1287.class1dhfinalproject.utils.Command

abstract class BaseFragment: Fragment() {

    abstract var command: MutableLiveData<Command>
}