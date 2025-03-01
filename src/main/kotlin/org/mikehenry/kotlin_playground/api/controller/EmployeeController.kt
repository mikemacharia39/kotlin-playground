package org.mikehenry.kotlin_playground.api.controller

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import jakarta.servlet.http.HttpServletResponse.SC_ACCEPTED
import jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN
import jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import jakarta.validation.Valid
import org.mikehenry.kotlin_playground.api.dto.params.EmployeeSearchParams
import org.mikehenry.kotlin_playground.api.dto.request.EmployeeRequestDto
import org.mikehenry.kotlin_playground.api.dto.request.RequestErrors
import org.mikehenry.kotlin_playground.api.dto.response.EmployeeResponseDto
import org.mikehenry.kotlin_playground.api.dto.response.FileDownloadResponseDto
import org.mikehenry.kotlin_playground.domain.service.EmployeeBulkUploadService
import org.mikehenry.kotlin_playground.domain.service.EmployeeService
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Validated
@RestController
@RequestMapping("/employees")
class EmployeeController(
    private val employeeService: EmployeeService,
    private val employeeBulkUploadService: EmployeeBulkUploadService
) {

    @PostMapping(
        consumes = ["application/json"],
        produces = ["application/json", "application/problem+json"]
    )
    @ApiOperation("Adds a new employee record")
    @ApiResponses(
        ApiResponse(code = SC_ACCEPTED, message = "Accepted.\n Successfully added employee record"),
        ApiResponse(
            code = SC_BAD_REQUEST, message = "Bad Request.\n Possible reasons: \n" +
                    "1) The request does not have all the required properties. \n" +
                    "2) The request properties are not on the required format. ", response = RequestErrors::class
        ),
        ApiResponse(
            code = SC_FORBIDDEN,
            message = "Forbidden.\n Possible reasons: \n" +
                    "1) The user does not have permission to access upload lock information",
            response = RequestErrors::class
        ),
        ApiResponse(
            code = SC_INTERNAL_SERVER_ERROR,
            message = "Internal Error.\n There was an internal error while processing the request",
            response = RequestErrors::class
        )
    )
    fun addEmployee(@Valid @RequestBody employeeRequestDto: EmployeeRequestDto): EmployeeResponseDto =
        employeeService.addEmployee(employeeRequestDto)

    @PostMapping("/bulk", consumes = ["multipart/form-data"])
    fun bulkUploadEmployees(@RequestPart(value = "file", required = true) file: MultipartFile) {
        employeeBulkUploadService.uploadEmployees(file)
    }

    @PostMapping("/id-document/{employeeId}", consumes = ["multipart/form-data"])
    @ResponseStatus(HttpStatus.CREATED)
    fun saveEmployeeIDDocument(@PathVariable(name = "employeeId") employeeId: Long, @RequestPart("file") file: MultipartFile) {
        employeeService.saveEmployeeIDDocument(employeeId, file)
    }

    @GetMapping("/{employeeId}")
    fun getEmployee(@PathVariable(name = "employeeId") employeeId: Long): EmployeeResponseDto = employeeService.getEmployee(employeeId)

    @GetMapping("/id-document/{employeeId}")
    fun getEmployeeIDDocument(@PathVariable(name = "employeeId") employeeId: Long): FileDownloadResponseDto = employeeService.getEmployeeIDDocument(employeeId)

    @GetMapping("/all")
    fun getAllEmployees(pageable: Pageable): Page<EmployeeResponseDto> = employeeService.getAllEmployees(pageable)

    @DeleteMapping("/{employeeId}")
    fun deleteEmployee(@PathVariable(name = "employeeId") employeeId: Long) {
        employeeService.deleteEmployee(employeeId)
    }

    @GetMapping("/search")
    fun searchEmployees(
        @SpringQueryMap employeeSearchParams: EmployeeSearchParams,
        pageable: Pageable
    ): Page<EmployeeResponseDto> = employeeService.searchEmployees(employeeSearchParams, pageable)
}
