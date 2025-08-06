package com.drygin.popcornplan.domain.util

/**
 * Created by Drygin Nikita on 06.08.2025.
 */
sealed class ApiResult<out T> {
    data class Success<T>(val data: T): ApiResult<T>()
    data class Error(val message: String, val cause: Throwable? = null): ApiResult<Nothing>()
}