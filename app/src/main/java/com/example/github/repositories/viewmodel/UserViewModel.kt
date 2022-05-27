package com.example.github.repositories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.model.BaseResponse
import com.example.github.repositories.model.RepositoryDTO
import com.example.github.repositories.model.UserDTO
import com.example.github.repositories.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {


    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val user: LiveData<BaseResponse<UserDTO>>
        get() = userRepository.user


    val repositories: LiveData<BaseResponse<List<RepositoryDTO>>>
        get() = userRepository.repositories

    fun fetchRepositories() {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.fetchRepositories()
            isLoading.postValue(false)
        }
    }

    fun fetchUser(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.fetchUser(login)
        }
    }


}