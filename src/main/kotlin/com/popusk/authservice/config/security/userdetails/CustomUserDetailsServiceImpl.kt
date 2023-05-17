package com.popusk.authservice.config.security.userdetails

import com.popusk.authservice.dao.user.UserEntity
import com.popusk.authservice.dao.user.UserRepo
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsServiceImpl(
    private val userRepo: UserRepo,
) : CustomUserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val entity = userRepo.findByUsername(username) ?: throw UsernameNotFoundException("User not Found")
        return UserDetails(entity.username, entity.password, entity.isActive)
    }

    override fun saveUser(userEntity: UserEntity): UserEntity {
        return userRepo.save(userEntity)
    }
}