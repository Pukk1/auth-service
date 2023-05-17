package com.popusk.authservice.service.auth

import com.popusk.authservice.api.v1.http.auth.LoginRequestView
import com.popusk.authservice.api.v1.http.auth.LoginResponseView
import com.popusk.authservice.api.v1.http.auth.RegisterRequestView
import com.popusk.authservice.config.security.jwt.JwtUtils
import com.popusk.authservice.config.security.userdetails.CustomUserDetailsService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val jwtUtils: JwtUtils,
) : AuthService {
    override fun login(loginRequestView: LoginRequestView): LoginResponseView {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequestView.username,
                    loginRequestView.password,
                )
            )
        } catch (e: Exception) {
            throw Exception("Bad Credentials")
        }

        val user: UserDetails = userDetailsService.loadUserByUsername(loginRequestView.username)
        val token: String = jwtUtils.generateToken(user.username)
        return LoginResponseView(token)
    }

    override fun register(registerRequestView: RegisterRequestView) {

    }
}