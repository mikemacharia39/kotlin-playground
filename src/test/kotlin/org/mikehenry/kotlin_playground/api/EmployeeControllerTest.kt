package org.mikehenry.kotlin_playground.api

import KotlinPlaygroundApplication
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.serpro69.kfaker.Faker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mikehenry.kotlin_playground.api.controller.EmployeeController
import org.mikehenry.kotlin_playground.mock.mockEmployeeRequest
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ActiveProfiles("unittest")
@TestPropertySource(locations = ["classpath:application-unittest.yml"])
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [KotlinPlaygroundApplication::class])
@ContextConfiguration(classes = [KotlinPlaygroundApplication::class])
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
//    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var employeeController: EmployeeController

    @BeforeEach
    fun setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build()
        objectMapper = ObjectMapper()
    }

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