import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mikehenry.kotlin_playground.api.dto.request.AddressRequestDto
import org.mikehenry.kotlin_playground.api.dto.request.DepartmentRequestDto
import org.mikehenry.kotlin_playground.api.dto.request.EmployeeRequestDto
import org.mikehenry.kotlin_playground.domain.entity.Department
import org.mikehenry.kotlin_playground.domain.entity.Employee
import org.mikehenry.kotlin_playground.domain.enumeration.AddressType
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeStatus
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType
import org.mikehenry.kotlin_playground.domain.mapper.EmployeeMapper
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

    @Mock
    private lateinit var employeeMapper: EmployeeMapper

    @InjectMocks
    private lateinit var employeeService: EmployeeService

    @Test
    fun `should return all employees`() {
        val employees = listOf(Employee("1", "Mike",
            "Henry", "", EmployeeType.FULL_TIME, EmployeeStatus.ACTIVE,
            Department("Science")
        ))
        val request =  EmployeeRequestDto(
            firstName = "Mike",
            lastName = "Henry",
            emailAddress = "mh@com",
            addresses = listOf(
                AddressRequestDto(
                AddressType.HOME, "Home", "", "Kenya", "Naivasha", "", "Box"
                )
            ),
            phoneNumber = "07101988888",
            department = DepartmentRequestDto("SCIENCE")
        )

        `when`(employeeRepository.findAll()).thenReturn(employees)

        val result = employeeService.addEmployee(request)

        assertThat(result.emailAddress).isEqualTo(request.emailAddress)
    }
}