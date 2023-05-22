package com.popusk.authservice.config.security.userdetails

import com.popusk.authservice.dao.user.UserEntity
import com.popusk.authservice.dao.user.UserId
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.transaction.annotation.Transactional

interface CustomUserDetailsService : UserDetailsService {
    fun getUserActiveJwt(username: String): String
    fun saveUser(userDetails: UserDetails, id: UserId? = null, activeJwt: String = ""): UserDetails
    @Transactional
    fun updateUserActiveJwtByUsername(username: String, activeJwt: String): UserEntity
}