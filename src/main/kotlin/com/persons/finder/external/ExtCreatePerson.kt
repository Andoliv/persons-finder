package com.persons.finder.external

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.persons.finder.constants.ApiExceptionMessages.PERSON_NAME_NOT_EMPTY
import javax.validation.constraints.NotBlank

data class ExtCreatePerson @JsonCreator constructor(

    @field:NotBlank(message = PERSON_NAME_NOT_EMPTY)
    @JsonProperty(required = true)
    val name: String
)