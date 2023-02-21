package org.mikehenry.kotlin_playground.api.controller

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.mikehenry.kotlin_playground.api.dto.request.EmployeeRequestDto
import org.mikehenry.kotlin_playground.api.dto.request.RequestErrors
import org.mikehenry.kotlin_playground.api.dto.response.EmployeeResponseDto
import org.mikehenry.kotlin_playground.domain.service.EmployeeService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse.SC_ACCEPTED
import javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import javax.servlet.http.HttpServletResponse.SC_FORBIDDEN
import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/employees")
class EmployeeController(
    private val employeeService: EmployeeService
) {

    @PostMapping(consumes = ["application/json"],
                produces = ["application/json", "application/problem+json"])
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
    fun addEmployee(@RequestBody @Valid employeeRequestDto: EmployeeRequestDto): EmployeeResponseDto = employeeService.addEmployee(employeeRequestDto)
}
