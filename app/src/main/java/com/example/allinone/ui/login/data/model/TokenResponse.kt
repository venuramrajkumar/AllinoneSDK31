package com.example.allinone.ui.login.data.model

data class TokenResponse(
    val token_type: String,
    val expires_in: String,
    val access_token: String?,
    val refresh_token: String?
)