package com.persons.finder.mapper

import com.persons.finder.data.Location
import com.persons.finder.external.ExtLocation
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface LocationMapper {
    fun toDto(location: Location): ExtLocation
    fun toData(extLocation: ExtLocation): Location
}