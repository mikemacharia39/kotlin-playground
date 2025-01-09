package org.mikehenry.kotlin_playground.api.dto.request

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.Setter
import lombok.NoArgsConstructor
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType
import java.time.LocalDate
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class EmployeeRequestDto(
    @field:NotNull(message = "first name should not be blank")
    @field:Size(min = 2, max = 40)
    val firstName: String? = null,
    @field:NotNull
    @field:Size(min = 2, max = 40)
    val lastName: String? = null,
    @field:NotNull
    val dateOfBirth: LocalDate = LocalDate.now(),
    @field:NotNull
    @field:Size(min = 2, max = 40)
    val emailAddress: String? = null,

    val addresses: List<AddressRequestDto> = listOf(AddressRequestDto()),

    @field:NotNull
    @field:Size(min = 10)
    val phoneNumber: String? = null,

    val employeeType: EmployeeType = EmployeeType.FULL_TIME,

    val department: DepartmentRequestDto = DepartmentRequestDto()
)
