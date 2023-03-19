package org.mikehenry.kotlin_playground.domain.mapper

import org.mikehenry.kotlin_playground.api.dto.request.EmployeeRequestDto
import org.mikehenry.kotlin_playground.api.dto.response.EmployeeResponseDto
import org.mikehenry.kotlin_playground.domain.entity.Employee
import org.springframework.stereotype.Component

@Component
class EmployeeMapper(
    private val addressMapper: AddressMapper,
    private val departmentMapper: DepartmentMapper
) {
    fun mapDtoToEntity(employeeRequestDto: EmployeeRequestDto): Employee {
        return Employee(
            firstName = employeeRequestDto.firstName,
            lastName = employeeRequestDto.lastName,
            emailAddress = employeeRequestDto.emailAddress,
            addresses = addressMapper.mapDTOsToEntities(employeeRequestDto.addresses),
            phoneNumber = employeeRequestDto.phoneNumber,
            employeeType = employeeRequestDto.employeeType,
            department = departmentMapper mapDTOToEntity employeeRequestDto.department
        )
    }

    fun mapEntityToDto(employee: Employee): EmployeeResponseDto {
        return EmployeeResponseDto(
            employeeId = employee.id,
            firstName = employee.firstName,
            lastName = employee.lastName,
            emailAddress = employee.emailAddress,
            phoneNumber = employee.phoneNumber,
            employeeType = employee.employeeType,
            department = departmentMapper mapEntityToDTO employee.department,
            addresses = addressMapper.mapEntitiesToDTOs(employee.addresses)
        )
    }


}