package com.popusk.authservice.config.security.userdetails

import com.popusk.authservice.dao.user.UserId
import org.springframework.security.core.userdetails.UserDetailsService

interface CustomUserDetailsService : UserDetailsService {
    fun saveUser(userDetails: UserDetails, id: UserId? = null): UserDetails
}