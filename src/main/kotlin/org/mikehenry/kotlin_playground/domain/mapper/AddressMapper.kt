package org.mikehenry.kotlin_playground.domain.mapper

import org.mikehenry.kotlin_playground.api.dto.request.AddressRequestDto
import org.mikehenry.kotlin_playground.domain.entity.Address
import org.springframework.stereotype.Component

@Component
class AddressMapper {
    fun mapDTOsToEntities(addressRequestDtos: List<AddressRequestDto>): List<Address> {
        return addressRequestDtos.map { this mapDTOToEntity it }
    }

    private infix fun mapDTOToEntity(addressRequestDto: AddressRequestDto): Address {
        return Address(
            addressType = addressRequestDto.addressType,
            addressLine1 = addressRequestDto.addressLine1,
            addressLine2 = addressRequestDto.addressLine2,
            country = addressRequestDto.country,
            city = addressRequestDto.city,
            state = addressRequestDto.state,
            postalBox = addressRequestDto.postalBox
        )
    }
}