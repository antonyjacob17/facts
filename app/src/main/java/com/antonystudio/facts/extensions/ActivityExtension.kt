package com.antonystudio.facts.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> AppCompatActivity.getViewModel(crossinline factory: () -> VM): VM =
    createViewModel(factory)

inline fun <reified VM : ViewModel> AppCompatActivity.createViewModel(crossinline factory: () -> VM): VM {

    @Suppress("UNCHECKED_CAST")
    val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM = factory() as VM
    }

    return ViewModelProvider(this, viewModelFactory)[VM::class.java]
}