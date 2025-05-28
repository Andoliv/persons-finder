package com.persons.finder.domain.mapper

import com.persons.finder.data.Person
import com.persons.finder.entity.PersonEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PersonEntityMapper {
    fun toDto(personEntity: PersonEntity): Person
    fun toEntity(person: Person): PersonEntity
}