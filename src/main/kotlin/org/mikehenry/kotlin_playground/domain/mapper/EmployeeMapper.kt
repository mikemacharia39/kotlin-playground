package org.mikehenry.kotlin_playground.domain.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mikehenry.kotlin_playground.api.dto.request.EmployeeRequestDto
import org.mikehenry.kotlin_playground.api.dto.response.EmployeeResponseDto
import org.mikehenry.kotlin_playground.domain.entity.Employee

@Mapper(
    componentModel = "spring",
    uses = [AddressMapper::class, DepartmentMapper::class]
)
abstract class EmployeeMapper{

    @Mapping(target = "employeeStatus", constant = "ACTIVE")
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    @Mapping(target = "addresses", source = "addresses", qualifiedByName = ["toAddressEntities"])
    @Mapping(target = "department", source = "department", qualifiedByName = ["toDepartmentEntity"])
    abstract infix fun mapDTOToEntity(employeeRequestDto: EmployeeRequestDto): Employee

    @Mapping(target = "employeeId", source = "id")
    @Mapping(target = "addresses", source = "addresses", qualifiedByName = ["toAddressDTOs"])
    @Mapping(target = "department", source = "department", qualifiedByName = ["toDepartmentDTO"])
    abstract infix fun mapEntityToDTO(employee: Employee): EmployeeResponseDto

    fun mapDTOsToEntities(employeeRequestDtos: List<EmployeeRequestDto>): List<Employee> {
        return employeeRequestDtos.map { this mapDTOToEntity it }
    }

    fun mapEntitiesToDTOs(employees: List<Employee>): List<EmployeeResponseDto> {
        return employees.map { this mapEntityToDTO it }
    }
}