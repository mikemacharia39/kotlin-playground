package org.mikehenry.kotlin_playground.domain.exception

import org.mikehenry.kotlin_playground.api.dto.request.RequestErrors
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvise: ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders,
                                              status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val requestErrors = RequestErrors(
            type = "https://mikehenry-maina.com/problems/kotlin-playgroung/constraint-violation",
            title = ex.bindingResult.allErrors.first().defaultMessage,
            status = HttpStatus.BAD_REQUEST,
            message = "error.http.400",
            params = ex.bindingResult.allErrors.first().objectName
        )
        return ResponseEntity(requestErrors, HttpStatus.BAD_REQUEST)
    }
}