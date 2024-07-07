package com.istudio.auth.data.models

import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test

class RegisterRequestTest {

    @Test
    fun `test RegisterRequest serialization`() {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val registerRequest = RegisterRequest(email, password)

        // When
        val json = Json.encodeToString(RegisterRequest.serializer(), registerRequest)
        val deserializedRegisterRequest = Json.decodeFromString(RegisterRequest.serializer(), json)

        // Then
        assertEquals(registerRequest, deserializedRegisterRequest)
    }

    @Test
    fun `test RegisterRequest email field`() {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val registerRequest = RegisterRequest(email, password)

        // Then
        assertEquals(email, registerRequest.email)
    }

    @Test
    fun `test RegisterRequest password field`() {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val registerRequest = RegisterRequest(email, password)

        // Then
        assertEquals(password, registerRequest.password)
    }
}