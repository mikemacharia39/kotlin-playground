package org.mikehenry.kotlin_playground.domain.exception

import org.zalando.problem.Status

class InvalidInputProblem(
    errorKey: String,
    params: Map<String, Any> = emptyMap()
): BaseProblem(status = Status.BAD_REQUEST, errorKey = errorKey, details = params)