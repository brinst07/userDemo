package com.brinst.userdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserDemoApplication

fun main(args: Array<String>) {
    runApplication<UserDemoApplication>(*args)
}
