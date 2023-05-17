package com.popusk.authservice.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtUtil: JwtUtils,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val reqTokenHeader = request.getHeader("Authorization")
        var userName: String? = null
        val jwtToken: String? = null
        if (reqTokenHeader != null && reqTokenHeader.startsWith("Bearer ")) {
            val tokenWithoutBearer = reqTokenHeader.substring(7)
            try {
                userName = jwtutil.extractUsername(tokenWithoutBearer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val user: UserDetails = userDetailsService.loadUserByUsername(userName)
            if (userName != null && SecurityContextHolder.getContext().authentication == null) {
                val usernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(user, null, user.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            } else {
                //any message
                println("Token not validated!!")
            }
        }
        filterChain.doFilter(request, response)
    }
}