package org.mikehenry.kotlin_playground.configuration

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ThymeleafLayoutConfig {

    @Bean
    fun layoutDialect(): LayoutDialect {
        return LayoutDialect()
    }
}