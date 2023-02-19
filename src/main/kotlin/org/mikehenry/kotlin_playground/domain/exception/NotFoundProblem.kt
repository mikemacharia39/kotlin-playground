package org.mikehenry.kotlin_playground.domain.exception

import org.zalando.problem.Status
import java.net.URI

class NotFoundProblem(
    errorKey: String,
    params: Map<String, Any> = emptyMap()
): BaseProblem(URI.create("https://example.com/not-found"), null, Status.NOT_FOUND, errorKey, params)