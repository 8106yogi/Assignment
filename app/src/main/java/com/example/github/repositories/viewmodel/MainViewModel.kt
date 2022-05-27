package com.example.github.repositories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.model.BaseResponse
import com.example.github.repositories.model.RepositoryDTO
import com.example.github.repositories.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {


    val repositories: LiveData<BaseResponse<List<RepositoryDTO>>>
        get() = mainRepository.repositories


    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)


    fun fetchItems() {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.fetchItems()
            isLoading.postValue(false)
        }
    }


}