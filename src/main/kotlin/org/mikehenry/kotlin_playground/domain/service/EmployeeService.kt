package org.mikehenry.kotlin_playground.domain.service

import org.mikehenry.kotlin_playground.api.dto.request.EmployeeRequestDto
import org.mikehenry.kotlin_playground.api.dto.response.EmployeeResponseDto
import org.mikehenry.kotlin_playground.api.dto.response.FileDownloadResponseDto
import org.mikehenry.kotlin_playground.domain.exception.NotFoundProblem
import org.mikehenry.kotlin_playground.domain.mapper.EmployeeMapper
import org.mikehenry.kotlin_playground.domain.repository.EmployeeRepository
import org.mikehenry.kotlin_playground.domain.specification.searchEmployee
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

const val EMPLOYEE_FILE_PREFIX = "employee_"

@Service
@Transactional
class EmployeeService(
    private val employeeMapper: EmployeeMapper,
    private val employeeRepository: EmployeeRepository,
    private val s3Service: S3Service,

    @Value("\${application.aws.s3.employee-document.bucket}")
    private val employeeDocumentBucket: String
) {
    @Transactional(readOnly = false)
    fun addEmployee(employeeRequestDto: EmployeeRequestDto): EmployeeResponseDto {
        val employee = employeeMapper.mapDTOToEntity(employeeRequestDto)
        val response = employeeRepository.save(employee)

        return employeeMapper.mapEntityToDTO(response)
    }

    fun getEmployee(employeeId: Long): EmployeeResponseDto {
        val employee = employeeRepository.findById(employeeId)
            .orElseThrow { throw NotFoundProblem("error.employee.not.found", mapOf("employeeId" to employeeId)) }

        return employeeMapper.mapEntityToDTO(employee)
    }

    fun getAllEmployees(pageable: Pageable): Page<EmployeeResponseDto> {
        val employees = employeeRepository.findAll(pageable)
        return employees.map { employeeMapper.mapEntityToDTO(it) }
    }

    fun searchEmployees(
        employeeName: String?,
        employeeIds: List<Long>?,
        phoneNumber: String?,
        departmentName: String?,
        address: String?,
        pageable: Pageable
    ): Page<EmployeeResponseDto> {
        return employeeRepository.findAll(
            searchEmployee(employeeName, employeeIds, phoneNumber, departmentName, address), pageable
        ).map { employeeMapper.mapEntityToDTO(it) }
    }

    fun saveEmployeeIDDocument(employeeId: Long, file: MultipartFile) {
        s3Service.uploadFile("$EMPLOYEE_FILE_PREFIX$employeeId", file, employeeDocumentBucket)
    }

    fun getEmployeeIDDocument(employeeId: Long): FileDownloadResponseDto {
        return FileDownloadResponseDto(
            url = s3Service.getDownloadUrl("$EMPLOYEE_FILE_PREFIX$employeeId", employeeDocumentBucket).toString()
        )
    }
}