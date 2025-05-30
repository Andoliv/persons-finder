package com.persons.finder.external

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

data class ExtCreateLocation @JsonCreator constructor(

    @JsonIgnore
    var personId: Long,

    @JsonProperty(required = true)
    val latitude: Double,

    @JsonProperty(required = true)
    val longitude: Double
)