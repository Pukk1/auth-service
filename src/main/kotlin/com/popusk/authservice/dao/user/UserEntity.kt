package com.popusk.authservice.dao.user

import com.popusk.authservice.config.security.userdetails.UserDetails
import jakarta.persistence.*

typealias UserId = Long

@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UserId?,
    @Column(unique = true)
    var username: String,
    var password: String,
    var isActive: Boolean,
    @Column(length = 3000)
    var activeJwt: String,
) {
    fun toDetails() = UserDetails(username, password, isActive)
}