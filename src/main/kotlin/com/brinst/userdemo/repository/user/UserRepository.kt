package com.brinst.userdemo.repository.user

import com.brinst.userdemo.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User
    fun findByUserNameOrEmail(username: String, email: String): User?
}