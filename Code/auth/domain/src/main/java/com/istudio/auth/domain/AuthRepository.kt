package com.istudio.auth.domain

import com.istudio.core.domain.util.DataError
import com.istudio.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}