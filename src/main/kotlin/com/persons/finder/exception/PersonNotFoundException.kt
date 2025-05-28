package com.persons.finder.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class PersonNotFoundException : RuntimeException {

    constructor(id: Long) : super("Person with id $id not found")

    constructor(name: String) : super("Person with name $name not found")

    constructor(id: Long, name: String) : super("Person with id $id and name $name not found")
}