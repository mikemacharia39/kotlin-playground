package org.mikehenry.kotlin_playground.api.dto.request

import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class EmployeeRequestDto(
    @field:NotNull(message = "first name should not be blank")
    @field:Size(min = 2, max = 40)
    val firstName: String,
    @field:NotNull
    val lastName: String,
    @field:NotNull
    @field:Size(min = 2, max = 40)
    val emailAddress: String,

    val addresses: List<AddressRequestDto>,

    @field:NotNull
    val phoneNumber: String,

    val employeeType: EmployeeType = EmployeeType.FULL_TIME,

    val department: DepartmentRequestDto
)
