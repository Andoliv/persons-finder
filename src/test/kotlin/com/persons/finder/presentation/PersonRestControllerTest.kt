package com.persons.finder.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import com.persons.finder.data.Location
import com.persons.finder.data.Person
import com.persons.finder.domain.services.LocationsService
import com.persons.finder.domain.services.PersonsService
import com.persons.finder.exception.PersonNotFoundException
import com.persons.finder.external.ExtCreateLocation
import com.persons.finder.external.ExtCreatePerson
import com.persons.finder.external.ExtLocation
import com.persons.finder.external.ExtPerson
import com.persons.finder.mapper.LocationMapper
import com.persons.finder.mapper.PersonMapper
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PersonRestController::class)
class PersonRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var personsService: PersonsService

    @MockBean
    private lateinit var personMapper: PersonMapper

    @MockBean
    private lateinit var locationMapper: LocationMapper

    @MockBean
    private lateinit var locationsService: LocationsService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun saveLocationShouldReturnCreatedLocation() {
        val extCreateLocation = ExtCreateLocation(1L, 10.0, 20.0)
        val location = Location(1L, 10.0, 20.0)
        val savedLocation = Location(1L, 10.0, 20.0)
        val extLocation = ExtLocation(1L, 10.0, 20.0)

        given(locationMapper.toData(extCreateLocation)).willReturn(location)
        given(locationsService.addLocation(location)).willReturn(savedLocation)
        given(locationMapper.toDto(savedLocation)).willReturn(extLocation)

        mockMvc.perform(
            put("/api/v1/persons/1/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(extCreateLocation))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun saveLocationShouldThrowPersonNotFoundException() {
        val personId = 99L
        val extCreateLocation = ExtCreateLocation(personId, 10.0, 20.0)
        val location = Location(personId, 10.0, 20.0)

        given(locationMapper.toData(extCreateLocation)).willReturn(location)
        given(locationsService.addLocation(location)).willThrow(PersonNotFoundException(personId))

        mockMvc.perform(
            put("/api/v1/persons/$personId/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(extCreateLocation))
        )
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun createPersonShouldReturnCreatedPerson() {
        val extCreatePerson = ExtCreatePerson("Test")
        val person = Person(1L, "Test")
        val savedPerson = Person(1L, "Test")
        val extPerson = ExtPerson(1L, "Test")

        given(personMapper.toData(extCreatePerson)).willReturn(person)
        given(personsService.save(person)).willReturn(savedPerson)
        given(personMapper.toDto(savedPerson)).willReturn(extPerson)

        mockMvc.perform(
            post("/api/v1/persons/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(extCreatePerson))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun getNearbyPersonsShouldReturnPersonIds() {
        val latitude = 100.0
        val longitude = 100.0
        val radiusKm = 10.0
        val location1 = Location(1L, 200.0, 200.0)
        val location2 = Location(2L, 100.0, 100.0)

        given(locationsService.findAround(latitude, longitude, radiusKm)).willReturn(listOf(location1, location2))

        mockMvc.perform(
            get("/api/v1/persons/nearby")
                .param("lat", latitude.toString())
                .param("lon", longitude.toString())
                .param("radiusKm", radiusKm.toString())
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun getPersonsShouldReturnAllPersonsWhenIdsIsNull() {
        val person1 = Person(1L, "A")
        val person2 = Person(2L, "B")
        val extPerson1 = ExtPerson(1L, "A")
        val extPerson2 = ExtPerson(2L, "B")

        given(personsService.getAll()).willReturn(listOf(person1, person2))
        given(personMapper.toDto(person1)).willReturn(extPerson1)
        given(personMapper.toDto(person2)).willReturn(extPerson2)

        mockMvc.perform(get("/api/v1/persons/"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }
}