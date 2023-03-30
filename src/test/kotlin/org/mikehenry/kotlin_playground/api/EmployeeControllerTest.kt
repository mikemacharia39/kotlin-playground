package org.mikehenry.kotlin_playground.api

/**
 * https://www.arhohuttunen.com/spring-boot-integration-testing/
 */

import KotlinPlaygroundApplication
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mikehenry.kotlin_playground.mock.mockEmployeeRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@TestPropertySource(locations = ["classpath:application-unittest.properties"])
@SpringBootTest(classes = [KotlinPlaygroundApplication::class])
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should save employee`() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/employees")
                .content(objectMapper.writeValueAsString(mockEmployeeRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.employeeId").isNumber)
            .andExpect(jsonPath("$.firstName").exists())
            .andExpect(jsonPath("$.addresses[*].addressLine1").isNotEmpty)
    }

}