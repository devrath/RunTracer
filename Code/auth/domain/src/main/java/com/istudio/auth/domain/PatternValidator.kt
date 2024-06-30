package com.istudio.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}