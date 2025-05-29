package com.persons.finder.external

import com.fasterxml.jackson.annotation.JsonProperty

class ExtLocation (
    @JsonProperty(required = false)
    var personId: Long,

    @JsonProperty(required = true)
    val latitude: Double,

    @JsonProperty(required = true)
    val longitude: Double
)