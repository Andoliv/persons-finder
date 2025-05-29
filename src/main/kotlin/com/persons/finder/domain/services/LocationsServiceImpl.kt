package com.persons.finder.domain.services

import com.persons.finder.data.Location
import com.persons.finder.domain.mapper.LocationEntityMapper
import com.persons.finder.domain.mapper.PersonEntityMapper
import com.persons.finder.mapper.PersonMapper
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
            ?: throw IllegalArgumentException("Person with id ${location.personId} does not exist.")

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

        // Haversine formula to calculate the distance between two points on the Earth
        fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val R = 6371.0
            val dLat = Math.toRadians(lat2 - lat1)
            val dLon = Math.toRadians(lon2 - lon1)
            val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2)
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

            return R * c
        }

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