package com.popusk.authservice.api.v1.http.auth

data class RegisterRequestView(
    val username: String,
    val password: String,
)

data class LoginRequestView(
    val username: String,
    val password: String,
)

data class LoginResponseView(
    val jwt: String,
)