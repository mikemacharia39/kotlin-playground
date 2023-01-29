package org.mikehenry.kotlin_playground.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("employees")
class EmployeeController {

    fun createEmployee(): Unit {
        // TODO something
    }
}