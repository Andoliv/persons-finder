package com.persons.finder.external

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ExtRestApiError(
    val code: Int,
    val status: String,
    val timestamp: LocalDateTime,
    val message: String,
    val reason: String
) {
    constructor(
        exception: Exception,
        httpStatus: HttpStatus,
        message: String
    ) : this(
        code = httpStatus.value(),
        status = httpStatus.reasonPhrase,
        timestamp = LocalDateTime.now(),
        message = message,
        reason = exception.localizedMessage ?: ""
    )
}