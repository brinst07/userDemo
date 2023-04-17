package com.brinst.userdemo.domain.user

import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

@Entity
class User(
    val username: String,
    var password: String,
    @Enumerated(EnumType.STRING)
    var role: Role,
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.ACTIVATE,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {

    fun getAuthorities(): User {
        return User(
            username,
            password,
            listOf(SimpleGrantedAuthority("ROLE_${role}"))
        )
    }
}

enum class Role {
    MEMBER,
    ADMIN
}

enum class UserStatus {
    ACTIVATE,
    DISABLED,
    DELETED,
}