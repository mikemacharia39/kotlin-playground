package org.mikehenry.kotlin_playground.api.dto.request

import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank

data class EmployeeRequestDto(
    @NotBlank
    @Max(40)
    val firstName: String,
    @NotBlank
    @Max(40)
    val lastName: String,
    @NotBlank
    @Max(100)
    val emailAddress: String,
    @NotBlank
    val addresses: List<AddressRequestDto>,
    @NotBlank
    val phoneNumber: String,
    @NotBlank
    val employeeType: EmployeeType = EmployeeType.FULL_TIME,
    @NotBlank
    val department: DepartmentRequestDto
)
