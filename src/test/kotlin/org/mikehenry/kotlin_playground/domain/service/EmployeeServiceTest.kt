import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mikehenry.kotlin_playground.domain.entity.Employee
import org.mikehenry.kotlin_playground.domain.mapper.EmployeeMapper
import org.mikehenry.kotlin_playground.domain.repository.EmployeeRepository
import org.mikehenry.kotlin_playground.domain.service.EmployeeService
import org.mikehenry.kotlin_playground.mock.mockEmployee
import org.mikehenry.kotlin_playground.mock.mockEmployeeRequest
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class EmployeeServiceTest {

    @Mock
    private lateinit var employeeRepository: EmployeeRepository

    private lateinit var employeeMapper: EmployeeMapper
    private lateinit var employeeService: EmployeeService

    @BeforeEach
    fun setup() {
        employeeService = EmployeeService(employeeMapper, employeeRepository)
    }

    @Test
    fun `should save and return saved employee data`() {
        `when`(employeeRepository.save(any(Employee::class.java))).thenReturn(mockEmployee())

        val response = employeeService.addEmployee(mockEmployeeRequest())

        assertNotNull(response)
        assertThat(response.department.departmentName).isNotEmpty
        assertThat(response.addresses.size).isEqualTo(1)
    }
}