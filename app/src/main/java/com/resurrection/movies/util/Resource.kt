package com.resurrection.movies.util

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

sealed class Resource<out T>(val status: Status, val data: T?, val message: Throwable?) {

    class Loading<T> : Resource<T>(status = Status.LOADING, data = null, message = null)
    class Success<T>(data: T?) : Resource<T>(status = Status.SUCCESS, data = data, message = null)
    class Error<T>(exception: Throwable) :
        Resource<T>(status = Status.ERROR, data = null, message = exception)

}