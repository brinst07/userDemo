package com.brinst.userdemo.controller

import com.brinst.userdemo.config.JwtService
import com.brinst.userdemo.service.user.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserController::class,JwtService::class, UserService::class)
@AutoConfigureRestDocs
class UserControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var jwtService: JwtService
    @MockBean
    private lateinit var userService: UserService

    @Test
    fun getUserList() {
        mockMvc.perform(get("/api/v1/user"))
            .andExpect(status().isOk)
            .andDo(
                document(
                    "getUserList",
                    responseFields(
                        fieldWithPath("[].id").description("user의 id").type(JsonFieldType.NUMBER),
                        fieldWithPath("[].username").description("user의 name").type(JsonFieldType.STRING),
                        fieldWithPath("[].role").description("user의 role").type(JsonFieldType.STRING),
                        fieldWithPath("[].status").description("user의 status").type(JsonFieldType.STRING)
                    )
                )
            )
    }
}