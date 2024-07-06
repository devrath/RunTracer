package com.istudio.core.data.networking.networking

import com.istudio.core.data.BuildConfig
import com.istudio.core.domain.AuthInfo
import com.istudio.core.domain.SessionStorage
import com.istudio.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory (
    private val sessionStorage: SessionStorage
) {

    fun build(): HttpClient {
        // We need to pass a engine to HttpClient, In our case we pass CIO engine
        return HttpClient(CIO) {
            // PLUGIN: Everything related to parsing
            install(ContentNegotiation) {
                // We are using kotlinX Serialization, But we can use MOSHI, GSON with appropriate dependencies
                json(
                    json = Json {
                        // If there are fields in json that are not in model object, We ignore it on parsing
                        ignoreUnknownKeys = true
                    }
                )
            }
            // PLUGIN: Logging Everything related to logging
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        // Whenever KTOR tries to log something, It will pick it up and pass it into Timber to log it.
                        Timber.d(message)
                    }
                }
                level = LogLevel.ALL
            }
            // PLUGIN: We can configure every single request looks like, It is similar to Intercepts in okhttp
            defaultRequest {
                contentType(ContentType.Application.Json)
                header("x-api-key", BuildConfig.API_KEY)
            }
            // PLUGIN: Here we set up the refresh mechanism for the tokens
            install(Auth) {
                // Our mechanism in backend is called bearer mechanism
                bearer {
                    // How the tokens look like: In our case we get from local storage
                    // Ktor attach to every single request
                    loadTokens {
                        val info = sessionStorage.get()
                        BearerTokens(
                            accessToken = info?.accessToken ?: "",
                            refreshToken = info?.refreshToken ?: ""
                        )
                    }
                    // Refresh token is called when the access token is expired
                    refreshTokens {
                        /**
                         * When the ktor calls the api and returns 401 status code, it will call the refresh token method
                         * and here we can call the refresh token and get the new token
                         */

                        val info = sessionStorage.get()
                        // Call the refresh token API and get the response
                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
                            route = "/accessToken",
                            body = AccessTokenRequest(
                                refreshToken = info?.refreshToken ?: "",
                                userId = info?.userId ?: ""
                            )
                        )

                        if(response is Result.Success) {
                            val newAuthInfo = AuthInfo(
                                accessToken = response.data.accessToken,
                                refreshToken = info?.refreshToken ?: "",
                                userId = info?.userId ?: ""
                            )
                            // Save the new tokens
                            sessionStorage.set(newAuthInfo)
                            // Now attach the new tokens to the new request
                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken
                            )
                        } else {
                            // If response is failure, Then set to a empty one so failure is sent by API
                            BearerTokens(
                                accessToken = "",
                                refreshToken = ""
                            )
                        }
                    }
                }
            }
        }
    }
}