package com.brinst.userdemo.domain.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class UserEntity(
    private val username: String = "",
    private var password: String = "",
    var email: String = "",
    @Enumerated(EnumType.STRING)
    var role: Role,
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.ACTIVATE,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(this.role.name))
    }
    override fun getPassword(): String {
        return this.password
    }
    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return this.status == UserStatus.ACTIVATE
    }

    fun updateStatus(userStatus: UserStatus) {
        this.status = userStatus
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