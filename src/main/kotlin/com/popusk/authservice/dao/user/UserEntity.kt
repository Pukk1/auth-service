package com.popusk.authservice.dao.user

import jakarta.persistence.*

typealias UserId = Long

@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UserId,
    @Column(unique = true)
    val username: String,
    val password: String,
    val isActive: Boolean,
)