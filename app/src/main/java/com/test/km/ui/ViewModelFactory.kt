package com.test.km.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.km.data.UsersRepository
import com.test.km.di.Injection
import com.test.km.ui.thirdscreen.ThirdViewModel

class ViewModelFactory(private val repository: UsersRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ThirdViewModel::class.java) -> {
                ThirdViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }
}