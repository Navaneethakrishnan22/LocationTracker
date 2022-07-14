package com.shop.mobile.locationtracker.utilities

import com.shop.mobile.locationtracker.utilities.ResponseStatus.*

data class ResultData<out T>(val status: ResponseStatus, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T?): ResultData<T> {
            return ResultData(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ResultData<T> {
            return ResultData(ERROR, data, msg)
        }

        fun <T> loading(data: T?): ResultData<T> {
            return ResultData(LOADING, data,null)
        }

    }
}
