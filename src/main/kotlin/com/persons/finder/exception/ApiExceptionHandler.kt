package com.persons.finder.exception

import com.persons.finder.constants.ApiExceptionMessages
import com.persons.finder.external.ExtRestApiError
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(exception: Exception): ResponseEntity<ExtRestApiError> {
        val error = ExtRestApiError(
            exception = exception,
            httpStatus = INTERNAL_SERVER_ERROR,
            message = exception.localizedMessage
        )

        return ResponseEntity.status(error.code).body(error)
    }

    @ExceptionHandler(PersonNotFoundException::class)
    fun handlePersonNotFoundException(exception: PersonNotFoundException): ResponseEntity<ExtRestApiError> {
        val error = ExtRestApiError(
            exception = exception,
            httpStatus = NOT_FOUND,
            message = exception.message ?: ApiExceptionMessages.PERSON_NOT_FOUND
        )

        return ResponseEntity.status(error.code).body(error)
    }

}