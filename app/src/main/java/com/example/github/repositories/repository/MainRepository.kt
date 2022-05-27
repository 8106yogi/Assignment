package com.example.github.repositories.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.github.repositories.model.BaseResponse
import com.example.github.repositories.model.RepositoryDTO
import com.example.github.repositories.network.GitHubEndpoints
import com.example.github.repositories.util.ORDER
import com.example.github.repositories.util.QUERY
import com.example.github.repositories.util.SORT
import kotlinx.coroutines.delay
import java.net.UnknownHostException
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val gitHubEndpoints: GitHubEndpoints
) {

    private val _repositories = MutableLiveData<BaseResponse<List<RepositoryDTO>>>()

    val repositories: LiveData<BaseResponse<List<RepositoryDTO>>>
        get() = _repositories


    //refresh is duplicate ,removed redundant refresh method
    suspend fun fetchItems() {
        delay(1_000) // This is to simulate network latency, please don't remove!
        try {
            val response = gitHubEndpoints.searchRepositories(QUERY, SORT, ORDER)

            if (response.isSuccessful && response.body() != null) {
                _repositories.postValue(BaseResponse.SUCCESS(response.body()?.items?.take(20)))
            } else {
                _repositories.postValue(BaseResponse.ERROR(response.errorBody()?.toString()))
            }

        } catch (e: UnknownHostException) {
            e.printStackTrace()
            _repositories.postValue(BaseResponse.NO_NETWORK(e.localizedMessage))

        } catch (e: Exception) {
            e.printStackTrace()
            _repositories.postValue(BaseResponse.ERROR(e.localizedMessage))
        }
    }


}