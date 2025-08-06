package com.drygin.popcornplan.data.remote

import com.drygin.popcornplan.domain.util.ApiResult
import com.drygin.popcornplan.network.error.ErrorResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException

/**
 * Created by Drygin Nikita on 06.08.2025.
 */
suspend fun <T> safeApiCall(
    call: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(call())
    } catch (e: RedirectResponseException) {
        ApiResult.Error("Redirect error: ${e.response.status}", e)
    } catch (e: ClientRequestException) {
        val errorBody = parseErrorResponse(e.response)
        ApiResult.Error(errorBody?.error ?: "Client error: ${e.response.status}", e)
    } catch (e: ServerResponseException) {
        val errorBody = parseErrorResponse(e.response)
        ApiResult.Error(errorBody?.error ?: "Server error: ${e.response.status}", e)
    } catch (e: HttpRequestTimeoutException) {
        ApiResult.Error("Timeout error", e)
    } catch (e: IOException) {
        ApiResult.Error("Network error", e)
    } catch (e: Exception) {
        ApiResult.Error("Unknown error", e)
    }
}

suspend fun parseErrorResponse(response: HttpResponse): ErrorResponse? {
    return try {
        response.body<ErrorResponse>()
    } catch (e: Exception) {
        null
    }
}