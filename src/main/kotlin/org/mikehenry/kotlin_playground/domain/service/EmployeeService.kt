package org.mikehenry.kotlin_playground.domain.service

import org.mikehenry.kotlin_playground.api.dto.request.EmployeeRequestDto
import org.mikehenry.kotlin_playground.api.dto.response.EmployeeResponseDto
import org.mikehenry.kotlin_playground.domain.mapper.EmployeeMapper
import org.mikehenry.kotlin_playground.domain.repository.EmployeeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeService(
    private val employeeMapper: EmployeeMapper
) {

    private lateinit var employeeRepository: EmployeeRepository

    @Transactional(readOnly = false)
    fun addEmployee(employeeRequestDto: EmployeeRequestDto): EmployeeResponseDto {
        val employee = employeeMapper.mapDtoToEntity(employeeRequestDto)
        val response = employeeRepository.save(employee)

        return employeeMapper.mapEntityToDto(response)
    }
}