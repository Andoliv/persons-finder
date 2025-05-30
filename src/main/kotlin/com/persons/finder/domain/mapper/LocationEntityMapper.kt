package com.persons.finder.domain.mapper

import com.persons.finder.data.Location
import com.persons.finder.entity.LocationEntity
import com.persons.finder.entity.PersonEntity
import org.springframework.stereotype.Component

@Component
class LocationEntityMapper {

    fun toDto(locationEntity: LocationEntity): Location {
        return Location(
            personId = locationEntity.person.id,
            latitude = locationEntity.latitude,
            longitude = locationEntity.longitude
        )
    }

    fun toEntity(location: Location): LocationEntity {
        return LocationEntity(
            person = PersonEntity(location.personId),
            latitude = location.latitude,
            longitude = location.longitude
        )
    }
}