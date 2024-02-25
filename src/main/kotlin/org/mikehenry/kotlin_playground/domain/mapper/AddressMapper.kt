package org.mikehenry.kotlin_playground.domain.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.mikehenry.kotlin_playground.api.dto.request.AddressRequestDto
import org.mikehenry.kotlin_playground.api.dto.response.AddressResponseDto
import org.mikehenry.kotlin_playground.domain.entity.Address

@Mapper(componentModel = "spring")
abstract class AddressMapper {

    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    abstract fun mapDTOToEntity(addressRequestDto: AddressRequestDto): Address

    @Mapping(target = "addressId", source = "id")
    abstract fun mapEntityToDTO(address: Address): AddressResponseDto

    @Named("toAddressEntities")
    fun mapDTOsToEntities(addressRequestDtos: List<AddressRequestDto>): List<Address> {
        return addressRequestDtos.map { mapDTOToEntity(it) }
    }

    @Named("toAddressDTOs")
    fun mapEntitiesToDTOs(addresses: List<Address>): List<AddressResponseDto> {
        return addresses.map { mapEntityToDTO(it) }
    }
}