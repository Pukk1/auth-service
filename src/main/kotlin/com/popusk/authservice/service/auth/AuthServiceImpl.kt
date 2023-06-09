package com.popusk.authservice.service.auth

import com.popusk.authservice.api.v1.http.auth.LoginRequestView
import com.popusk.authservice.api.v1.http.auth.LoginResponseView
import com.popusk.authservice.api.v1.http.auth.RegisterRequestView
import com.popusk.authservice.config.security.jwt.JwtUtils
import com.popusk.authservice.config.security.userdetails.CustomUserDetailsService
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val jwtUtils: JwtUtils,
    private val encoder: BCryptPasswordEncoder,
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
        userDetailsService.updateUserActiveJwtByUsername(user.username, token)
        return LoginResponseView(token)
    }

    override fun register(registerRequestView: RegisterRequestView) {
        userDetailsService.saveUser(
            userDetails = com.popusk.authservice.config.security.userdetails.UserDetails(
                registerRequestView.username,
                encoder.encode(registerRequestView.password),
                true,
            )
        )
    }

    override fun validateJWT(jwt: String): Claims {
        val claims = jwtUtils.validateToken(jwt)

        val activeJwt: String = userDetailsService.getUserActiveJwt(claims["username"] as String)

        if (activeJwt != jwt) {
            throw IllegalArgumentException("Jwt for user was changed")
        }

        return claims
    }
}