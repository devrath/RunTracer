package com.istudio.core.data.networking.networking

import com.istudio.core.data.BuildConfig
import com.istudio.core.domain.util.DataError
import com.istudio.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import timber.log.Timber

suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    body: Request
): Result<Response, DataError.Network> {
    return safeCall {
        post {
            url(constructRoute(route))
            setBody(body)
        }
    }
}

suspend inline fun <reified Response : Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError.Network> {
    val result: Result<T, DataError.Network> = try {
        val response = execute()
        responseToResult(response)
    } catch (e: UnresolvedAddressException) {
        Timber.e(e, "Address not resolved while calling an API endpoint")
        Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        Timber.e(e, "Serialization error caused while calling an API endpoint")
        Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Timber.e(e, "Generic error caused while calling an API endpoint")
        Result.Error(DataError.Network.UNKNOWN)
    }
    return result
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Network> {
    return when (response.status.value) {
        in HttpStatus.OK..HttpStatus.NO_CONTENT -> Result.Success(response.body<T>())
        HttpStatus.UNAUTHORIZED -> Result.Error(DataError.Network.UNAUTHORIZED)
        HttpStatus.REQUEST_TIMEOUT -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        HttpStatus.CONFLICT -> Result.Error(DataError.Network.CONFLICT)
        HttpStatus.PAYLOAD_TOO_LARGE -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        HttpStatus.TOO_MANY_REQUESTS -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in HttpStatus.INTERNAL_SERVER_ERROR..HttpStatus.GATEWAY_TIMEOUT -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(route: String): String {
    return when {
        route.contains(BuildConfig.BASE_URL) -> route
        route.startsWith("/") -> BuildConfig.BASE_URL + route
        else -> BuildConfig.BASE_URL + "/$route"
    }
}
