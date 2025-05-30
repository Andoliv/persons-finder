package com.persons.finder.exception

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = NOT_FOUND)
class PersonNotFoundException : RuntimeException {

    constructor(id: Long) : super("Person with id $id not found")

    constructor(name: String) : super("Person with name $name not found")

    constructor(id: Long, name: String) : super("Person with id $id and name $name not found")
}