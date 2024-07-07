package com.istudio.auth.data.validators

import android.util.Patterns
import com.istudio.auth.domain.PatternValidator

object EmailPatternValidator: PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}