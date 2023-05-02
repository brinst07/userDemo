package com.brinst.userdemo.service.user

import com.brinst.userdemo.config.JwtService
import com.brinst.userdemo.domain.user.Role
import com.brinst.userdemo.domain.user.User
import com.brinst.userdemo.dto.user.LoginDTO
import com.brinst.userdemo.dto.user.UserDTO
import com.brinst.userdemo.dto.user.UserRequestDTO
import com.brinst.userdemo.repository.user.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {
    fun getUserList(): List<UserDTO> {
        return userRepository.findAll().map { UserDTO.convertDTO(it) }
    }

    fun getUserById(id: Long): UserDTO {
        return userRepository.getUserById(id).let { UserDTO.convertDTO(it) }
    }

    fun registerUser(userRequestDTO: UserRequestDTO): String {
        //TODO 중복체크 로직 추가 할 것
        val user = userRepository.save(
            User(
                username = userRequestDTO.username,
                email = userRequestDTO.email,
                password = passwordEncoder.encode(userRequestDTO.password),
                role = Role.MEMBER
            )
        )
        return jwtService.generateToken(user)
    }

    fun login(loginDTO: LoginDTO): String {
        this.authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDTO.username,
                loginDTO.password
            )
        )

        val user = userRepository.findByUsername(loginDTO.username)
        return jwtService.generateToken(user)
    }


    fun UserRepository.getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow(::IllegalArgumentException)
    }
}