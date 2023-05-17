package com.popusk.authservice.config.security

import com.popusk.authservice.model.UserId
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
class UserDetails(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UserId,
    @Column(unique = true)
    val username: String,
    val password: String,
    val isActive: Boolean,
) {
//    override fun getAuthorities(): List<GrantedAuthority> {
//        return listOf()
//    }
//
//    override fun getPassword(): String {
//        return password
//    }
//
//    override fun getUsername(): String {
//        return username
//    }
//
//    override fun isAccountNonExpired(): Boolean {
//        return isActive
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        return isActive
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        return isActive
//    }
//
//    override fun isEnabled(): Boolean {
//        return isActive
//    }

}