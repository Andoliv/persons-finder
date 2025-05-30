package com.persons.finder.domain.services

import com.persons.finder.data.Location
import com.persons.finder.domain.mapper.LocationEntityMapper
import com.persons.finder.domain.mapper.PersonEntityMapper
import com.persons.finder.domain.utils.haversine
import com.persons.finder.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationsServiceImpl(
    private val locationEntityMapper: LocationEntityMapper,
    private val locationRepository: LocationRepository,
    private val personsService: PersonsService,
    private val personEntityMapper: PersonEntityMapper
) : LocationsService {


    override fun addLocation(location: Location): Location {
        val person = personsService.getById(location.personId)

        val locationEntity = locationEntityMapper.toEntity(location)
        locationEntity.person = personEntityMapper.toEntity(person) // Ensure the person is set in the entity

        val savedLocation = locationRepository.save(locationEntity)

        return locationEntityMapper.toDto(savedLocation)
    }

    override fun removeLocation(locationReferenceId: Long) {
        TODO("Not yet implemented")
    }

    override fun findAround(latitude: Double, longitude: Double, radiusInKm: Double): List<Location> {
        val latitudeMin = latitude - (radiusInKm / 111.32) // Approximate conversion from km to degrees
        val latitudeMax = latitude + (radiusInKm / 111.32)
        val longitudeMin = longitude + (radiusInKm / (111.32 * Math.cos(Math.toRadians(latitude))))
        val longitudeMax = longitude - (radiusInKm / (111.32 * Math.cos(Math.toRadians(latitude))))

        val locations = locationRepository.findByLatitudeBetweenAndLongitudeBetween(
            latitudeMin, latitudeMax, longitudeMin, longitudeMax
        )

        val locationsWithDistance = locations.map { location ->
            val distance = haversine(latitude, longitude, location.latitude, location.longitude)
            location to distance
        }

        val filteredLocations = locationsWithDistance.filter { (_, distance) ->
            distance <= radiusInKm
        }

        val sortedLocations = filteredLocations.sortedBy { (_, distance) ->
            distance
        }

        return sortedLocations.map { (location, _) ->
            locationEntityMapper.toDto(location)
        }
    }

}