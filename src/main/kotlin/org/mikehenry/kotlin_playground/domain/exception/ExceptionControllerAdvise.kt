package org.mikehenry.kotlin_playground.domain.exception

import org.springframework.core.annotation.Order
import org.springframework.http.ProblemDetail
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.net.URI
import java.net.URISyntaxException


@Order(-1)
@ControllerAdvice
class ExceptionControllerAdvise {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @Throws(URISyntaxException::class)
    fun handleHttpMessageNotReadable(exception: HttpMessageNotReadableException?): ProblemDetail {
        val problemDetail = ProblemDetail.forStatus(400)
        problemDetail.type = URI.create("https://mikehenry-maina.com/problems/kotlin-playgroung/invalid-data")
        problemDetail.title = "error.request.invalid-data"
        problemDetail.detail = exception?.message
        return problemDetail
    }
}