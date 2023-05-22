package com.popusk.authservice.api.v1.http.auth

import com.popusk.authservice.service.auth.AuthService
import io.jsonwebtoken.Claims
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(@RequestBody loginData: LoginRequestView): LoginResponseView {
        return authService.login(loginData)
    }

    @PostMapping("/register")
    fun register(@RequestBody registerData: RegisterRequestView) {
        authService.register(registerData)
    }

    @PostMapping(value = ["/jwt/validate"])
    fun validateJwt(@RequestHeader("Authentication") jwt: String): Claims {
        return authService.validateJWT(jwt)
    }
}