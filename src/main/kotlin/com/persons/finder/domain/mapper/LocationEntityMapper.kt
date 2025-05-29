package com.persons.finder.domain.mapper

import com.persons.finder.data.Location
import com.persons.finder.entity.LocationEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface LocationEntityMapper {

    @Mapping(source = "person.id", target = "personId")
    fun toDto(locationEntity: LocationEntity): Location

    @Mapping(source = "personId", target = "person.id")
    fun toEntity(location: Location): LocationEntity
}