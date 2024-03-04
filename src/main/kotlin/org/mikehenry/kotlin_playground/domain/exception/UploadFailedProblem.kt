package org.mikehenry.kotlin_playground.domain.exception

import org.zalando.problem.Status

class UploadFailedProblem(
    errorKey: String,
    params: Map<String, Any> = emptyMap()
): BaseProblem(status = Status.INTERNAL_SERVER_ERROR, errorKey = errorKey, details = params)