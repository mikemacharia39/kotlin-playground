package org.mikehenry.kotlin_playground.api.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank

data class DepartmentRequestDto(
    @NotBlank
    @Max(50)
    val departmentName: String,

    @Max(255)
    val departmentDescription: String? = null
)
