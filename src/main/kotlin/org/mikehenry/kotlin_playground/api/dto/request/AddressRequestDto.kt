package org.mikehenry.kotlin_playground.api.dto.request

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.Setter
import org.mikehenry.kotlin_playground.domain.enumeration.AddressType
import javax.validation.constraints.NotBlank

@Getter
@Setter
@AllArgsConstructor
@Builder
class AddressRequestDto(

    val addressType: AddressType = AddressType.HOME,
    @NotBlank
    val addressLine1: String,
    val addressLine2: String? = null,
    @NotBlank
    val country: String,
    @NotBlank
    val city: String,
    val state: String? = null,
    @NotBlank
    val postalBox: String
)
