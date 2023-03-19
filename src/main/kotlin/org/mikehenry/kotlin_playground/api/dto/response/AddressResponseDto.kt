package org.mikehenry.kotlin_playground.api.dto.response

import org.mikehenry.kotlin_playground.domain.enumeration.AddressType

data class AddressResponseDto(
    val addressId: Long,
    val addressType: AddressType,
    val addressLine1: String,
    val addressLine2: String? = null,
    val country: String,
    val city: String,
    val state: String? = null,
    val postalBox: String
)