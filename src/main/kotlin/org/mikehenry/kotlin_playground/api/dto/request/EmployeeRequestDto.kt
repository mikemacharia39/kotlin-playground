package org.mikehenry.kotlin_playground.api.dto.request

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.Setter
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType
import java.time.LocalDate
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Builder
@AllArgsConstructor
@Getter
@Setter
class EmployeeRequestDto(
    @field:NotNull(message = "first name should not be blank")
    @field:Size(min = 2, max = 40)
    val firstName: String,
    @field:NotNull
    val lastName: String,
    @field:NotNull
    val dateOfBirth: LocalDate,
    @field:NotNull
    @field:Size(min = 2, max = 40)
    val emailAddress: String,

    val addresses: List<AddressRequestDto>,

    @field:NotNull
    val phoneNumber: String,

    val employeeType: EmployeeType = EmployeeType.FULL_TIME,

    val department: DepartmentRequestDto
)
