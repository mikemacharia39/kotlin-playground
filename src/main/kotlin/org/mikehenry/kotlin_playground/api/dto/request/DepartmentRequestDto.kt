package org.mikehenry.kotlin_playground.api.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class DepartmentRequestDto(
    @NotBlank
    @field:Size(min = 2, max = 40)
    val departmentName: String,

    @field:Size(max = 255)
    val departmentDescription: String? = null
)
