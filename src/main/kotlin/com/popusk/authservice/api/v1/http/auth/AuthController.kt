package com.popusk.authservice.api.v1.http.auth

import com.popusk.authservice.service.auth.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController(
    private val authService: AuthService,
) {
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(@RequestBody loginData: LoginRequestView): ResponseEntity<*> {
        val response = authService.login(loginData)
        return ResponseEntity.ok<Any>(response)
    }

    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(@RequestBody registerData: RegisterRequestView) {
        val response = authService.register(registerData)
    }
}