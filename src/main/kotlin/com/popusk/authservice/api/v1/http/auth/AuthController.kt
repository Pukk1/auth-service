package com.popusk.authservice.controller.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController {
    @Autowired
    private val userService: CustomUserService? = null

    @Autowired
    private val jwtUtil: JwtUtil? = null

    @Autowired
    private val authenticationManager: AuthenticationManager? = null
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(@RequestBody jwtRequest: JwtRequest): ResponseEntity<*> {
        try {
            authenticationManager!!.authenticate(
                UsernamePasswordAuthenticationToken(
                    jwtRequest.getUsername(),
                    jwtRequest.getPassword()
                )
            )
        } catch (e: Exception) {
            throw Exception("Bad Credentials")
        }

        //fine code
        val user: UserDetails = userService.loadUserByUsername(jwtRequest.getUsername())
        val token: String = jwtUtil.generateToken(user)
        println("Token = $token")
        return ResponseEntity.ok<Any>(JwtResponse(token))
    }

    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(@RequestBody user: User?): ResponseEntity<*> {
        return ResponseEntity.ok(userService.saveUser(user))
    }

    //Only ADMIN role can access this
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = ["/welcome"], method = [RequestMethod.GET])
    fun test(): String {
        return "Welcome with token !!"
    }

    //Only USER role can access this
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = ["/welcomeuser"], method = [RequestMethod.GET])
    fun testuser(): String {
        return "Welcome with token USER !!"
    }
}