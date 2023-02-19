package org.mikehenry.kotlin_playground.api.dto.response

import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType

data class EmployeeResponseDto(
    val employeeId: Long,
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
    val phoneNumber: String,
    val employeeType: EmployeeType,
    val department: DepartmentResponseDto
)