package com.popusk.authservice.config.security.userdetails

import com.popusk.authservice.dao.user.UserEntity
import org.springframework.security.core.userdetails.UserDetailsService

interface CustomUserDetailsService : UserDetailsService {
    fun saveUser(userEntity: UserEntity): UserEntity
}