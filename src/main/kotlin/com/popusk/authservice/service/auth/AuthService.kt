package com.popusk.authservice.service.auth

import com.popusk.authservice.api.v1.http.auth.LoginRequestView
import com.popusk.authservice.api.v1.http.auth.LoginResponseView
import com.popusk.authservice.api.v1.http.auth.RegisterRequestView

interface AuthService {
    fun login(loginRequestView: LoginRequestView): LoginResponseView
    fun register(registerRequestView: RegisterRequestView)
}