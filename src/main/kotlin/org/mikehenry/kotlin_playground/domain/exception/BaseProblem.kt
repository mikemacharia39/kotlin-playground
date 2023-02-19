package org.mikehenry.kotlin_playground.domain.exception

import mu.KotlinLogging
import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import java.net.URI
import java.util.Locale
import java.util.ResourceBundle

abstract class BaseProblem(
    uri: URI,
    title: String? = null,
    status: Status,
    errorKey: String,
    details: Map<String, Any> = emptyMap()
): AbstractThrowableProblem(uri, title, status, errorKey, null, null, details) {
    constructor(
        uri: URI,
        status: Status,
        errorKey: String,
        details: Map<String, Any> = emptyMap()
    ): this(uri, null, status, errorKey, details)

    private val log = KotlinLogging.logger { }

    override fun getCause(): Exceptional? = super.cause?.getCause()

    override fun getTitle(): String? =
        try {
            ResourceBundle.getBundle("messages", Locale.getDefault())
                .getString(detail ?: "error.default")
        } catch (e: Exception) {
            log.warn { "Couldn't find message resource for key: $detail" }
        }.toString()
}
