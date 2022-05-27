package com.example.github.repositories.model

sealed class BaseResponse<T>(val data: T?, val errorMessage: String?) {

    class LOADING<T> : BaseResponse<T>(data = null, errorMessage = null)

    class NO_NETWORK<T>(errorMessage: String?) :
        BaseResponse<T>(data = null, errorMessage = errorMessage)

    class SUCCESS<T>(data: T?) : BaseResponse<T>(data = data, errorMessage = null)

    class ERROR<T>(errorMessage: String?) :
        BaseResponse<T>(data = null, errorMessage = errorMessage)

}