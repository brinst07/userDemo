package com.brinst.userdemo.controller

import com.brinst.userdemo.dto.user.LoginDTO
import com.brinst.userdemo.dto.user.UserDTO
import com.brinst.userdemo.dto.user.UserRequestDTO
import com.brinst.userdemo.service.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getUserList(): ResponseEntity<List<UserDTO>> {
        return ResponseEntity(userService.getUserList(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long) : ResponseEntity<UserDTO> {
        return ResponseEntity(userService.getUserById(id), HttpStatus.OK)
    }

    @PostMapping
    fun registerUser(@RequestBody userRequestDTO: UserRequestDTO) : ResponseEntity<String> {
        return ResponseEntity(userService.registerUser(userRequestDTO), HttpStatus.OK)
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody loginDTO: LoginDTO) : ResponseEntity<String> {
        return ResponseEntity(userService.login(loginDTO), HttpStatus.OK)
    }

}