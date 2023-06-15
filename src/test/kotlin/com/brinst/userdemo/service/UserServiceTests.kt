package com.brinst.userdemo.service

import com.brinst.userdemo.service.user.UserService
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(UserService::class)
class UserServiceTests : DescribeSpec({

    describe("user 생성시") {
        context("올바른 데이터가 전달되면") {

            it ("생성된 user 정보가 리턴된다.")
        }
    }
})