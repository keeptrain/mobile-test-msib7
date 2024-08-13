package com.test.km.ui.thirdscreen

import androidx.lifecycle.ViewModel
import com.test.km.data.UsersRepository

class ThirdViewModel (private val repository : UsersRepository) : ViewModel() {
    fun getUsers(page: Int, perPage: Int) = repository.getUsers(page, perPage)
}
