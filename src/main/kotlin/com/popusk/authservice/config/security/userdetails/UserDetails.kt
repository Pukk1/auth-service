package com.popusk.authservice.config.security.userdetails

import com.popusk.authservice.dao.user.UserEntity
import com.popusk.authservice.dao.user.UserId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetails(
    private val username: String,
    private val password: String,
    private val isActive: Boolean,
) : UserDetails {
    override fun getAuthorities(): List<GrantedAuthority> {
        return listOf()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return isActive
    }

    override fun isAccountNonLocked(): Boolean {
        return isActive
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isActive
    }

    override fun isEnabled(): Boolean {
        return isActive
    }

    fun toEntity(id: UserId?, activeJwt: String) = UserEntity(
        id,
        username,
        password,
        isEnabled,
        activeJwt,
    )
}