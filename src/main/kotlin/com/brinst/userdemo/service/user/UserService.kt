package com.brinst.userdemo.service.user

import com.brinst.userdemo.config.JwtService
import com.brinst.userdemo.domain.user.Role
import com.brinst.userdemo.domain.user.UserEntity
import com.brinst.userdemo.dto.user.LoginDTO
import com.brinst.userdemo.dto.user.UserDTO
import com.brinst.userdemo.dto.user.UserRequestDTO
import com.brinst.userdemo.repository.user.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {
    /**
     * user list를 조회하는 메소드
     */
    @Transactional(readOnly = true)
    fun getUserList(): List<UserDTO> {
        return userRepository.findAll().map { UserDTO.convertDTO(it) }
    }

    /**
     * user의 id로 user를 조회하는 메소드
     */
    @Transactional(readOnly = true)
    fun getUserById(id: Long): UserDTO {
        return userRepository.getUserById(id).let { UserDTO.convertDTO(it) }
    }

    /**
     * 회원가입 메소드
     */
    @Transactional
    fun registerUser(userRequestDTO: UserRequestDTO): String {
        //username, email 중복 체크
        userRepository.findByUserNameOrEmail(userRequestDTO.username, userRequestDTO.email)
            ?: throw IllegalArgumentException("해당 id, email은 중복되었습니다.")
        //user 저장
        val userEntity = userRepository.save(
            UserEntity(
                username = userRequestDTO.username,
                email = userRequestDTO.email,
                password = passwordEncoder.encode(userRequestDTO.password),
                role = Role.MEMBER
            )
        )
        //token 발급
        return jwtService.generateToken(userEntity)
    }

    /**
     * login 메소드
     */
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

    /**
     * returnType optional 대신 User로 리턴하기 위한 메소드
     */
    fun UserRepository.getUserById(id: Long): UserEntity {
        return userRepository.findById(id).orElseThrow(::IllegalArgumentException)
    }
}