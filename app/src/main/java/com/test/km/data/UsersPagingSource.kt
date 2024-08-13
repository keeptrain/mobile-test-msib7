package com.test.km.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.km.data.api.ApiService
import com.test.km.data.response.DataItem

class UsersPagingSource (private val apiService: ApiService,private val pageSize: Int):
    PagingSource<Int, DataItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getUsers(page = position, perPage = pageSize)
            val listUsers = responseData.data?.map {
                DataItem(
                    id = it?.id,
                    email = it?.email,
                    firstName = it?.firstName,
                    lastName = it?.lastName,
                    avatar = it?.avatar
                )
            }  ?: emptyList()

            LoadResult.Page(
                data = listUsers,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (listUsers.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}