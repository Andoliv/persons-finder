package com.persons.finder.mapper

import com.persons.finder.data.Person
import com.persons.finder.external.ExtCreatePerson
import com.persons.finder.external.ExtPerson
import org.springframework.stereotype.Component

@Component
class PersonMapper {
    fun toDto(person: Person): ExtPerson {
        return ExtPerson(
            id = person.id,
            name = person.name
        )
    }

    fun toData(extCreatePerson: ExtCreatePerson): Person {
        return Person(
            id = 0,
            name = extCreatePerson.name
        )
    }
}