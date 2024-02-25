package org.mikehenry.kotlin_playground.domain.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.mikehenry.kotlin_playground.api.dto.request.DepartmentRequestDto
import org.mikehenry.kotlin_playground.api.dto.response.DepartmentResponseDto
import org.mikehenry.kotlin_playground.domain.entity.Department

@Mapper(componentModel = "spring")
interface DepartmentMapper {

    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    @Named("toDepartmentEntity")
    fun mapDTOToEntity(departmentRequestDto: DepartmentRequestDto): Department

    @Mapping(target = "departmentId", source = "id")
    @Named("toDepartmentDTO")
    fun mapEntityToDTO(department: Department): DepartmentResponseDto
}