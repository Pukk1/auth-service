package com.popusk.authservice.config.security.userdetails

import com.popusk.authservice.dao.user.UserEntity
import com.popusk.authservice.dao.user.UserId
import com.popusk.authservice.dao.user.UserRepo
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CustomUserDetailsServiceImpl(
    private val userRepo: UserRepo,
) : CustomUserDetailsService {

    fun getUserByUsername(username: String) =
        userRepo.findByUsername(username) ?: throw UsernameNotFoundException("User not Found")


    override fun loadUserByUsername(username: String): UserDetails {
        val entity = getUserByUsername(username)
        return UserDetails(entity.username, entity.password, entity.isActive)
    }

    override fun getUserActiveJwt(username: String): String {
        val entity = getUserByUsername(username)
        return entity.activeJwt
    }

    override fun saveUser(userDetails: UserDetails, id: UserId?, activeJwt: String): UserDetails {
        val entity = userRepo.save(userDetails.toEntity(id, activeJwt))
        return entity.toDetails()
    }

    @Transactional
    override fun updateUserActiveJwtByUsername(username: String, activeJwt: String): UserEntity {
        val entity = getUserByUsername(username)
        entity.activeJwt = activeJwt
        return userRepo.save(entity)
    }
}