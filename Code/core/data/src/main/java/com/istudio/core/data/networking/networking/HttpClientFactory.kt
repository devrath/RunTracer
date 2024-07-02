package com.istudio.core.data.networking.networking

import com.istudio.core.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
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

class HttpClientFactory {

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
        }
    }
}