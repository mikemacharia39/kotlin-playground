package org.mikehenry.kotlin_playground.api.dto.request

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.Setter
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Getter
@Setter
@AllArgsConstructor
@Builder
class DepartmentRequestDto(
    @NotBlank
    @field:Size(min = 2, max = 40)
    val departmentName: String,

    @field:Size(max = 255)
    val departmentDescription: String? = null
)
