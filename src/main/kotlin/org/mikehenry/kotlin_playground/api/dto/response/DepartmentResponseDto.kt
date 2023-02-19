package org.mikehenry.kotlin_playground.api.dto.response

data class DepartmentResponseDto(
    val departmentId: Long,
    val departmentName: String,
    val departmentDescription: String? = null
)
