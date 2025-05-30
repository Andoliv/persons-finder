package com.persons.finder.mapper

import com.persons.finder.data.Location
import com.persons.finder.external.ExtCreateLocation
import com.persons.finder.external.ExtLocation
import org.springframework.stereotype.Component

@Component
class LocationMapper {
    fun toDto(location: Location): ExtLocation {
        return ExtLocation(
            personId = location.personId,
            latitude = location.latitude,
            longitude = location.longitude
        )
    }

    fun toData(extCreateLocation: ExtCreateLocation): Location {
        return Location(
            personId = extCreateLocation.personId,
            latitude = extCreateLocation.latitude,
            longitude = extCreateLocation.longitude
        )
    }
}