package com.test.km.ui.thirdscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.km.data.UsersRepository
import com.test.km.data.api.ApiConfig
import com.test.km.data.response.DataItem
import kotlinx.coroutines.flow.Flow

class ThirdViewModel: ViewModel() {
    private val userRepository = UsersRepository(ApiConfig.getApiService())
    val users: Flow<PagingData<DataItem>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { userRepository.getUserPagingSource(10) }
    ).flow.cachedIn(viewModelScope)
}

/*class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThirdViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/