package com.persons.finder.domain.services

import com.persons.finder.data.Location
import com.persons.finder.data.Person
import com.persons.finder.domain.mapper.LocationEntityMapper
import com.persons.finder.domain.mapper.PersonEntityMapper
import com.persons.finder.entity.LocationEntity
import com.persons.finder.entity.PersonEntity
import com.persons.finder.repository.LocationRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

class LocationsServiceImplTest {

    private lateinit var locationEntityMapper: LocationEntityMapper
    private lateinit var locationRepository: LocationRepository
    private lateinit var personsService: PersonsService
    private lateinit var personEntityMapper: PersonEntityMapper
    private lateinit var locationsService: LocationsServiceImpl

    @BeforeEach
    fun setUp() {
        locationEntityMapper = mock(LocationEntityMapper::class.java)
        locationRepository = mock(LocationRepository::class.java)
        personsService = mock(PersonsService::class.java)
        personEntityMapper = mock(PersonEntityMapper::class.java)
        locationsService = LocationsServiceImpl(
            locationEntityMapper,
            locationRepository,
            personsService,
            personEntityMapper
        )
    }

    @Test
    fun addLocationShouldSaveAndReturnLocation() {
        val location = Location(1L, 10.0, 20.0)
        val person = Person(1L, "Anderson Oliveira")
        val personEntity = PersonEntity()
        val locationEntity = LocationEntity()
        val savedEntity = LocationEntity()
        val mappedLocation = Location(1L, 10.0, 20.0)

        given(personsService.getById(1L)).willReturn(person)
        given(locationEntityMapper.toEntity(location)).willReturn(locationEntity)
        given(personEntityMapper.toEntity(person)).willReturn(personEntity)
        given(locationRepository.save(locationEntity)).willReturn(savedEntity)
        given(locationEntityMapper.toDto(savedEntity)).willReturn(mappedLocation)

        val result = locationsService.addLocation(location)

        assertEquals(mappedLocation, result)
        verify(personsService).getById(1L)
        verify(locationEntityMapper).toEntity(location)
        verify(personEntityMapper).toEntity(person)
        verify(locationRepository).save(locationEntity)
        verify(locationEntityMapper).toDto(savedEntity)
    }

    @Test
    fun findAroundShouldReturnFilteredLocations() {
        val lat = 100.0
        val lon = 100.0
        val radiusKm = 10.0
        val locationEntity1 = LocationEntity().apply { latitude = 200.0; longitude = 200.0 }
        val locationEntity2 = LocationEntity().apply { latitude = 100.0; longitude = 100.0 }
        val location1 = Location(1L, 200.0, 200.0)
        val location2 = Location(2L, 100.0, 100.0)

        given(
            locationRepository.findByLatitudeBetweenAndLongitudeBetween(
                anyDouble(), anyDouble(), anyDouble(), anyDouble()
            )
        ).willReturn(listOf(locationEntity1, locationEntity2))
        given(locationEntityMapper.toDto(locationEntity1)).willReturn(location1)
        given(locationEntityMapper.toDto(locationEntity2)).willReturn(location2)

        val result = locationsService.findAround(lat, lon, radiusKm)

        // Only locationEntity2 should be within radius (based on haversine)
        assertEquals(listOf(location2), result)
        verify(locationRepository).findByLatitudeBetweenAndLongitudeBetween(
            anyDouble(), anyDouble(), anyDouble(), anyDouble()
        )
    }
}