package org.mikehenry.kotlin_playground.api.dto.params

data class EmployeeSearchParams(
    val employeeName: String?,
    val employeeIds: List<Long>?,
    val phoneNumber: String?,
    val departmentName: String?,
    val address: String?
)