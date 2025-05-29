package com.persons.finder.mapper

import com.persons.finder.data.Person
import com.persons.finder.external.ExtPerson
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PersonMapper {
    fun toDto(person: Person): ExtPerson
    fun toData(extPerson: ExtPerson): Person
}