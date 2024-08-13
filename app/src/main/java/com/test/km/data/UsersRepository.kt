package com.test.km.data

import com.test.km.data.api.ApiService

class UsersRepository (private val apiService: ApiService) {

    fun getUserPagingSource(pageSize: Int) = UsersPagingSource(apiService,pageSize)
}