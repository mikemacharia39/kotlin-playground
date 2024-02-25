package org.mikehenry.kotlin_playground.domain.exception

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.NativeWebRequest
import org.zalando.problem.Problem
import org.zalando.problem.spring.web.advice.ProblemHandling

@ControllerAdvice
class ExceptionControllerAdvise : ProblemHandling {

    val log = KotlinLogging.logger { }

    override fun log(throwable: Throwable, problem: Problem, request: NativeWebRequest, status: HttpStatus) {
        if (problem is BaseProblem) {
            log.error { "Status: $status. Exception: ${problem.title} - ${problem.parameters}" }
        } else {
            log.error(throwable) { "Status: $status Handled exception: ${problem.title} - ${problem.detail} - ${problem.parameters}" }
        }
    }

    override fun handleMethodArgumentNotValid(
        exception: MethodArgumentNotValidException,
        request: NativeWebRequest
    ): ResponseEntity<Problem> {
        val fieldErrors = exception.bindingResult.fieldErrors
            .filter { it.code != null }
            .associateBy(
                { it.field },
                { it.code as Any }
            )
        return create(exception, InvalidInputProblem("error.invalid-input", fieldErrors), request)
    }

    override fun handleMessageNotReadableException(
        exception: HttpMessageNotReadableException,
        request: NativeWebRequest
    ): ResponseEntity<Problem> {
        return create(exception, InvalidInputProblem("error.request.invalid-input", mapOf("message" to (exception.message ?: ""))), request)
    }
}