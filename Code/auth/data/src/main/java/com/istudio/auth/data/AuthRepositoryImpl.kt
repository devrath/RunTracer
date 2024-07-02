package com.istudio.auth.data

import com.istudio.auth.domain.AuthRepository
import com.istudio.core.data.networking.networking.post
import com.istudio.core.domain.util.DataError
import com.istudio.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
): AuthRepository {

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}