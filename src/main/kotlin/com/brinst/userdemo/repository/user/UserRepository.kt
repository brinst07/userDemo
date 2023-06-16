package com.brinst.userdemo.repository.user

import com.brinst.userdemo.domain.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun findByUsernameOrEmail(username: String, email: String): UserEntity?
}