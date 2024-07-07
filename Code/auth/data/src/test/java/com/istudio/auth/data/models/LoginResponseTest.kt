package com.istudio.auth.data.models

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test

class LoginResponseTest {


    @Test
    fun `serialization and deserialization should be symmetric`() {
        // Given
        val response = LoginResponse(
            accessToken = "sampleAccessToken",
            refreshToken = "sampleRefreshToken",
            accessTokenExpirationTimestamp = 1625668800L, // Replace with actual timestamp
            userId = "sampleUserId"
        )

        // When
        val json = Json.encodeToString(response)
        val deserializedResponse = Json.decodeFromString<LoginResponse>(json)

        // Then
        assertEquals(response, deserializedResponse)
    }


}