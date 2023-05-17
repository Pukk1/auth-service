package com.popusk.authservice.repo

import com.popusk.authservice.config.security.UserDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : JpaRepository<UserDetails, Long> {
    fun findByUsername(username: String): UserDetails?
}