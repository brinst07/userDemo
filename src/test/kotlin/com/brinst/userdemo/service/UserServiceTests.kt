package com.brinst.userdemo.service

import com.brinst.userdemo.config.JwtService
import com.brinst.userdemo.domain.user.Role
import com.brinst.userdemo.domain.user.UserStatus
import com.brinst.userdemo.dto.user.LoginDTO
import com.brinst.userdemo.dto.user.UserRequestDTO
import com.brinst.userdemo.service.user.UserService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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
            it("생성된 user의 정보가 리턴된다.") {
                val registerUser = userService.registerUser(userRequestDTO)
                registerUser.role shouldBe Role.MEMBER
                registerUser.username shouldBe userRequestDTO.username
                registerUser.status shouldBe UserStatus.ACTIVATE
            }
        }

        context("중복된 데이터가 전달되면") {
            val userRequestDTO = UserRequestDTO(
                    username = "brinst",
                    email = "brinst@test.com",
                    password = "q1w2e3r4!"
            )
            it("에러가 발생한다.") {
                val exception = shouldThrow<IllegalArgumentException> {
                    userService.registerUser(userRequestDTO)
                }
                exception.message shouldBe "해당 id, email은 중복되었습니다."
            }
        }
    }

    describe("login시") {
        context("회원이 로그인을 하면") {
            val loginDTO = LoginDTO(
                    username = "brinst",
                    password = "q1w2e3r4!"
            )
            it("토큰이 리턴된다.") {
                val token = userService.login(loginDTO)
                val extractUsername = jwtService.extractUsername(token)

                token shouldNotBe null
                extractUsername shouldBe loginDTO.username
            }
        }

        context("회원이 아닌 user가 로그인을 하면") {
            val noUser = LoginDTO(
                    username = "hoonyeon",
                    password = "q1w2e3r4!"
            )
            it("에러가 발생한다.") {
                val exception = shouldThrow<IllegalArgumentException> {
                    userService.login(noUser)
                }

                exception.message shouldBe "user가 존재하지 않습니다."
            }
        }
    }

})