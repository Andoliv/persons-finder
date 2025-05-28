package com.persons.finder.domain.services

import com.persons.finder.constants.ApiExceptionMessages
import com.persons.finder.data.Person
import com.persons.finder.domain.mapper.PersonEntityMapper
import com.persons.finder.exception.PersonNotFoundException
import com.persons.finder.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonsServiceImpl(
    private val personRepository: PersonRepository,
    private val personEntityMapper: PersonEntityMapper
) : PersonsService {

    override fun getById(id: Long): Person {
        val personEntity = personRepository.findById(id)
            .orElseThrow { PersonNotFoundException(id) }

        return personEntityMapper.toDto(personEntity)
    }

    override fun save(person: Person): Person {
        val personEntity = personEntityMapper.toEntity(person)
        val savedPerson = personRepository.save(personEntity)

        return personEntityMapper.toDto(savedPerson)
    }

    override fun getAll(): List<Person> {
        val personEntities = personRepository.findAll()
        if (personEntities.isEmpty()) {
            return emptyList()
        }

        return personEntities.map { personEntityMapper.toDto(it) }
    }

}