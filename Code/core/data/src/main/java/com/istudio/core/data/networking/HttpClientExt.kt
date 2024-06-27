package com.istudio.core.data.networking

import com.istudio.core.data.BuildConfig
import com.istudio.core.domain.util.Result
import com.istudio.core.domain.util.DataError
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

object HttpStatus {
    const val OK = 200
    const val CREATED = 201
    const val ACCEPTED = 202
    const val NO_CONTENT = 204
    const val UNAUTHORIZED = 401
    const val REQUEST_TIMEOUT = 408
    const val CONFLICT = 409
    const val PAYLOAD_TOO_LARGE = 413
    const val TOO_MANY_REQUESTS = 429
    const val INTERNAL_SERVER_ERROR = 500
    const val BAD_GATEWAY = 502
    const val SERVICE_UNAVAILABLE = 503
    const val GATEWAY_TIMEOUT = 504
}

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
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
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
