package com.persons.finder.domain.mapper

import com.persons.finder.data.Person
import com.persons.finder.entity.PersonEntity
import org.springframework.stereotype.Component

@Component
class PersonEntityMapper {

    fun toDto(personEntity: PersonEntity): Person {
        return Person(
            id = personEntity.id,
            name = personEntity.name
        )
    }

    fun toEntity(person: Person): PersonEntity {
        return PersonEntity(
            id = person.id,
            name = person.name
        )
    }
}