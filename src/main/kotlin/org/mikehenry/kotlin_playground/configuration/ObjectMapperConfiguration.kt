package org.mikehenry.kotlin_playground.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.cfg.CoercionAction
import com.fasterxml.jackson.databind.cfg.CoercionInputShape
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.databind.json.JsonMapper.Builder
import com.fasterxml.jackson.databind.type.LogicalType
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.zalando.problem.jackson.ProblemModule
import org.zalando.problem.violations.ConstraintViolationProblemModule
import java.text.SimpleDateFormat

@Configuration
class ObjectMapperConfiguration {

    companion object {
        fun configure(): Builder {
            return JsonMapper.builder()
                .addModules(
                    JavaTimeModule(),
                    ProblemModule(),
                    ConstraintViolationProblemModule(),
                    ParameterNamesModule()
                )
                .defaultDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"))
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(FAIL_ON_UNKNOWN_PROPERTIES)
        }
    }

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val objectMapper: JsonMapper = configure().build()
        objectMapper.coercionConfigFor(LogicalType.Enum).setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull)
        return objectMapper
    }
}