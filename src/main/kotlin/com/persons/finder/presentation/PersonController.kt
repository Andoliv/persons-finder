package com.persons.finder.presentation

import com.persons.finder.domain.services.PersonsService
import com.persons.finder.external.ExtPerson
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
    private val personMapper: PersonMapper
) {

    /*
        TODO PUT API to update/create someone's location using latitude and longitude
        (JSON) Body
     */

    /*
        TODO POST API to create a 'person'
        (JSON) Body and return the id of the created entity
    */
    @PostMapping("")
    fun createPerson(@Valid @RequestBody extPerson: ExtPerson): ResponseEntity<ExtPerson> {
        try {

            val person = personMapper.toEntity(extPerson)
            val savedPerson = personsService.save(person)
            val responseExtPerson = personMapper.toDto(savedPerson)

            return ResponseEntity.status(HttpStatus.CREATED).body(responseExtPerson)
        } catch (e: Exception) {
            // Handle exception, e.g., log it or return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }


    /*
        TODO GET API to retrieve people around query location with a radius in KM, Use query param for radius.
        TODO API just return a list of persons ids (JSON)
        // Example
        // John wants to know who is around his location within a radius of 10km
        // API would be called using John's id and a radius 10km
     */

    /*
        TODO GET API to retrieve a person or persons name using their ids
        // Example
        // John has the list of people around them, now they need to retrieve everybody's names to display in the app
        // API would be called using person or persons ids
     */

    @GetMapping("")
    fun getPersons(): ResponseEntity<List<ExtPerson>> {
        val persons = personsService.getAll()
        val extPersons = persons.map { personMapper.toDto(it) }

        return if (extPersons.isNotEmpty()) {
            ResponseEntity.ok(extPersons)
        } else {
            ResponseEntity.noContent().build()
        }
    }

}