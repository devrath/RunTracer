package com.istudio.auth.data.models

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test

class LoginRequestTest {

    @Test
    fun `serialization and deserialization should work correctly`() {
        // Given
        val request = LoginRequest("test@example.com", "password123")

        // When
        val json = Json.encodeToString(request)
        val deserialized = Json.decodeFromString<LoginRequest>(json)

        // Then
        assertEquals(request, deserialized)
    }

    @Test
    fun `json serialization should match expected format`() {
        // Given
        val request = LoginRequest("test@example.com", "password123")

        // When
        val json = Json.encodeToString(request)

        // Then
        val expectedJson = """{"email":"test@example.com","password":"password123"}"""
        assertEquals(expectedJson, json)
    }

    @Test
    fun `create LoginRequest instance`() {
        // When
        val request = LoginRequest("test@example.com", "password123")

        // Then
        assertEquals("test@example.com", request.email)
        assertEquals("password123", request.password)
    }

}