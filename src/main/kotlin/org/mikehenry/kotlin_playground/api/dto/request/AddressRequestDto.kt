package org.mikehenry.kotlin_playground.api.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.mikehenry.kotlin_playground.domain.enumeration.AddressType

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class AddressRequestDto(
    val addressType: AddressType = AddressType.HOME,
    @NotBlank
    @field:Size(min = 2, max = 200)
    val addressLine1: String? = "",
    val addressLine2: String? = null,
    @NotBlank
    @field:Size(min = 2)
    val country: String? = "",
    @NotBlank
    @field:Size(min = 2)
    val city: String? = "",
    val state: String? = null,
    @NotBlank
    @field:Size(min = 2)
    val postalBox: String? = ""
)