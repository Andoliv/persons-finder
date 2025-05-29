package com.persons.finder.presentation

import com.persons.finder.data.Person
import com.persons.finder.domain.services.LocationsService
import com.persons.finder.domain.services.PersonsService
import com.persons.finder.external.ExtLocation
import com.persons.finder.external.ExtNearbyPeople
import com.persons.finder.external.ExtPerson
import com.persons.finder.mapper.LocationMapper
import com.persons.finder.mapper.PersonMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/persons")
class PersonController @Autowired constructor(
    private val personsService: PersonsService,
    private val personMapper: PersonMapper,
    private val locationMapper: LocationMapper,
    private val locationsService: LocationsService
) {

    /*
        TODO PUT API to update/create someone's location using latitude and longitude
        (JSON) Body
     */
    @PutMapping("/{id}/location")
    fun saveLocation(
        @Valid @RequestBody extLocation: ExtLocation,
        @PathVariable id: String
    ): ResponseEntity<ExtLocation> {
        extLocation.personId = id.toLong()
        val location = locationMapper.toData(extLocation)
        val responseLocation = locationsService.addLocation(location)

        return ResponseEntity.ok(locationMapper.toDto(responseLocation))
    }

    /*
        TODO POST API to create a 'person'
        (JSON) Body and return the id of the created entity
    */
    @PostMapping("")
    fun createPerson(@Valid @RequestBody extPerson: ExtPerson): ResponseEntity<ExtPerson> {
        val person = personMapper.toData(extPerson)
        val savedPerson = personsService.save(person)
        val responseExtPerson = personMapper.toDto(savedPerson)

        return ResponseEntity.status(HttpStatus.CREATED).body(responseExtPerson)
    }


    /*
        TODO GET API to retrieve people around query location with a radius in KM, Use query param for radius.
        TODO API just return a list of persons ids (JSON)
        // Example
        // John wants to know who is around his location within a radius of 10km
        // API would be called using John's id and a radius 10km
     */
    @GetMapping("/nearby")
    fun getNearbyPersons(
        @RequestParam("latitude") latitude: Double,
        @RequestParam("longitude") longitude: Double,
        @RequestParam("radiusKm", defaultValue = "10.0") radiusKm: Double
    ): ResponseEntity<ExtNearbyPeople> {
        val locations = locationsService.findAround(latitude, longitude, radiusKm)
        val personIds = locations.map { it.personId }.distinct()
        val extNearbyPeople = ExtNearbyPeople(personIds)

        return if (personIds.isNotEmpty()) {
            ResponseEntity.ok(extNearbyPeople)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    /*
        TODO GET API to retrieve a person or persons name using their ids
        // Example
        // John has the list of people around them, now they need to retrieve everybody's names to display in the app
        // API would be called using person or persons ids
     */
    @GetMapping("")
    fun getPersons(@RequestParam("id", required = false) ids: List<Long>?): ResponseEntity<List<ExtPerson>> {
        val people: List<Person> = if (ids.isNullOrEmpty()) {
            personsService.getAll()
        } else {
            ids.mapNotNull {
                try {
                    personsService.getById(it)
                } catch (e: Exception) {
                    null
                }
            }
        }

        val extPersons = people.map { personMapper.toDto(it) }

        return if (extPersons.isNotEmpty()) {
            ResponseEntity.ok(extPersons)
        } else {
            ResponseEntity.noContent().build()
        }

    }

}