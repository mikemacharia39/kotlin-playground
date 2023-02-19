import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mikehenry.kotlin_playground.domain.entity.Department
import org.mikehenry.kotlin_playground.domain.entity.Employee
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeStatus
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType
import org.mikehenry.kotlin_playground.domain.repository.EmployeeRepository
import org.mikehenry.kotlin_playground.domain.service.EmployeeService
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class EmployeeServiceTest {

    @Mock
    private lateinit var employeeRepository: EmployeeRepository

    @InjectMocks
    private lateinit var employeeService: EmployeeService

    @Test
    fun `should return all employees`() {
        val employees = listOf(Employee("1", "Mike",
            "Henry", "", EmployeeType.FULL_TIME, EmployeeStatus.ACTIVE,
            Department("Science")
        ))
        `when`(employeeRepository.findAll()).thenReturn(employees)

        val result = employeeService.addEmployee()

        assertThat(result).isEqualTo(employees)
    }
}