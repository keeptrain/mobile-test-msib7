package com.test.km.data

import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.test.km.data.api.ApiService
import kotlinx.coroutines.Dispatchers


class UsersRepository (private val apiService: ApiService) {

    fun getUsers(page: Int, perPage: Int) = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            val response = Pager(
                config = PagingConfig(
                    pageSize = perPage
                ),
                pagingSourceFactory = {
                    UsersPagingSource(apiService, page)
                }
            ).liveData
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error"))
        }
    }

    companion object {
        @Volatile
        private var instance: UsersRepository? = null

        fun getInstance(apiService: ApiService): UsersRepository =
            instance ?: synchronized(this) {
                instance ?: UsersRepository(apiService).also { instance = it }
            }
    }
}