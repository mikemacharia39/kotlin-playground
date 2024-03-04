package org.mikehenry.kotlin_playground.domain.exception

import org.zalando.problem.Status

class UploadFailedProblem(
    errorKey: String,
    params: Map<String, Any> = emptyMap()
): BaseProblem(status = Status.NOT_FOUND, errorKey = errorKey, details = params)