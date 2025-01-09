package org.mikehenry.kotlin_playground.api.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {
    @GetMapping(path = ["","/", "/index"])
    fun getHomePage(): String {
        return "index"
    }

    @GetMapping("/contact")
    fun getContactPage(): String {
        return "contact"
    }

    @GetMapping("/privacy")
    fun getPrivacyPage(): String {
        return "privacy"
    }
}