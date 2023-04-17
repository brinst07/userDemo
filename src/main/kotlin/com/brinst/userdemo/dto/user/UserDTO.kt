package com.brinst.userdemo.dto.user

import com.brinst.userdemo.domain.user.Role
import com.brinst.userdemo.domain.user.User
import com.brinst.userdemo.domain.user.UserStatus

data class UserDTO(
    val id : Long,
    val username : String,
    val role : Role,
    val status: UserStatus
) {
    companion object{
        fun convertDTO(user : User): UserDTO {
            return UserDTO(
                id = user.id!!,
                username = user.username,
                role = user.role,
                status = user.status,
            )
        }
    }
}

data class UserRequestDTO (
    val username : String,
    val role : Role,
    val status: UserStatus
)