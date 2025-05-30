package com.persons.finder.presentation

import com.persons.finder.data.Person
import com.persons.finder.domain.services.LocationsService
import com.persons.finder.domain.services.PersonsService
import com.persons.finder.exception.PersonNotFoundException
import com.persons.finder.external.*
import com.persons.finder.mapper.LocationMapper
import com.persons.finder.mapper.PersonMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class PersonRestController @Autowired constructor(
    private val personsService: PersonsService,
    private val personMapper: PersonMapper,
    private val locationMapper: LocationMapper,
    private val locationsService: LocationsService
) : PersonController {

    @PutMapping("/{id}/location", consumes = [APPLICATION_JSON_VALUE])
    override fun saveLocation(
        @Valid @RequestBody extCreateLocation: ExtCreateLocation,
        @PathVariable id: String
    ): ResponseEntity<ExtLocation> {
        extCreateLocation.personId = id.toLong()
        val location = locationMapper.toData(extCreateLocation)
        val responseLocation = locationsService.addLocation(location)
        val responseExtLocation = locationMapper.toDto(responseLocation)

        return ResponseEntity.status(CREATED).body(responseExtLocation)
    }

    @PostMapping("", consumes = [APPLICATION_JSON_VALUE])
    override fun createPerson(@Valid @RequestBody extCreatePerson: ExtCreatePerson): ResponseEntity<ExtPerson> {
        val person = personMapper.toData(extCreatePerson)
        val savedPerson = personsService.save(person)
        val responseExtPerson = personMapper.toDto(savedPerson)

        return ResponseEntity.status(CREATED).body(responseExtPerson)
    }

    @GetMapping("/nearby")
    override fun getNearbyPersons(
        @RequestParam("lat") latitude: Double,
        @RequestParam("lon") longitude: Double,
        @RequestParam("radiusKm", defaultValue = "10.0") radiusKm: Double
    ): ResponseEntity<ExtNearbyPeople> {
        val locations = locationsService.findAround(latitude, longitude, radiusKm)
        val personIds = locations.map { it.personId }.distinct()
        val responseExtNearby = ExtNearbyPeople(personIds)

        return ResponseEntity.ok(responseExtNearby)
    }

    @GetMapping("")
    override fun getPersons(@RequestParam("id", required = false) ids: List<Long>?): ResponseEntity<ExtPersons> {
        val people: List<Person> = if (ids.isNullOrEmpty()) {
            personsService.getAll()
        } else {
            ids.mapNotNull {
                try {
                    personsService.getById(it)
                } catch (e: PersonNotFoundException) {
                    null
                }
            }
        }

        val extPersonList = people.map { personMapper.toDto(it) }
        val responseExtPersonList = ExtPersons(extPersonList)

        return ResponseEntity.ok(responseExtPersonList)
    }

}