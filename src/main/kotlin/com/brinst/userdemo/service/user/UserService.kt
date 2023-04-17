package com.brinst.userdemo.service.user

import com.brinst.userdemo.domain.user.User
import com.brinst.userdemo.dto.user.UserDTO
import com.brinst.userdemo.repository.user.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUserList(): List<UserDTO> {
        return userRepository.findAll().map { UserDTO.convertDTO(it) }
    }

    fun getUserById(id: Long): UserDTO {
        return userRepository.getUserById(id).let { UserDTO.convertDTO(it) }
    }

    fun UserRepository.getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow(::IllegalArgumentException)
    }
}