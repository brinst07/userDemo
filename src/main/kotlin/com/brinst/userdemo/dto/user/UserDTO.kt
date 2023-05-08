package com.brinst.userdemo.dto.user

import com.brinst.userdemo.domain.user.Role
import com.brinst.userdemo.domain.user.UserEntity
import com.brinst.userdemo.domain.user.UserStatus

data class UserDTO(
    val id: Long,
    val username: String,
    val role: Role,
    val status: UserStatus
) {
    companion object {
        fun convertDTO(userEntity: UserEntity): UserDTO {
            return UserDTO(
                id = userEntity.id!!,
                username = userEntity.username,
                role = userEntity.role,
                status = userEntity.status,
            )
        }
    }
}

data class UserRequestDTO(
    val username: String,
    val email: String,
    val password: String
)

data class LoginDTO(
    val username: String,
    val password: String
)