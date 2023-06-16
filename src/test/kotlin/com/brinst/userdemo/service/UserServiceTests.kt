package com.brinst.userdemo.service

import com.brinst.userdemo.config.JwtService
import com.brinst.userdemo.dto.user.UserRequestDTO
import com.brinst.userdemo.service.user.UserService
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTests(
        private val userService: UserService,
        private val jwtService: JwtService
) : DescribeSpec({

    describe("user 생성시") {
        context("올바른 데이터가 전달되면") {
            //올바른 데이터
            val userRequestDTO = UserRequestDTO(
                    username = "brinst",
                    email = "brinst@test.com",
                    password = "q1w2e3r4!"
            )
            it("생성된 user 정보가 리턴된다.") {
                val registerUserToken = userService.registerUser(userRequestDTO)
                val extractUsername = jwtService.extractUsername(registerUserToken)
                extractUsername shouldBe userRequestDTO.username
            }
        }
    }
})