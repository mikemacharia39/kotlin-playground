package org.mikehenry.kotlin_playground.domain.mapper

import org.mikehenry.kotlin_playground.api.dto.request.DepartmentRequestDto
import org.mikehenry.kotlin_playground.api.dto.response.DepartmentResponseDto
import org.mikehenry.kotlin_playground.domain.entity.Department
import org.springframework.stereotype.Component

@Component
class DepartmentMapper {

    fun mapDTOsToEntities(departmentRequestDtos: List<DepartmentRequestDto>): List<Department> {
        return departmentRequestDtos.map { this mapDTOToEntity it }
    }

    infix fun mapDTOToEntity(departmentRequestDto: DepartmentRequestDto): Department {
        return Department(
            departmentName = departmentRequestDto.departmentName,
            departmentDescription = departmentRequestDto.departmentDescription
        )
    }

    infix fun mapEntityToDTO(department: Department): DepartmentResponseDto {
        return DepartmentResponseDto(
            departmentId = department.id,
            departmentName = department.departmentName,
            departmentDescription = department.departmentDescription
        )
    }
}