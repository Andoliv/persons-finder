package com.persons.finder.domain.services

import com.persons.finder.data.Person
import com.persons.finder.domain.mapper.PersonEntityMapper
import com.persons.finder.entity.PersonEntity
import com.persons.finder.exception.PersonNotFoundException
import com.persons.finder.repository.PersonRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*
import java.util.*

class PersonsServiceImplTest {

    private lateinit var personRepository: PersonRepository
    private lateinit var personEntityMapper: PersonEntityMapper
    private lateinit var personsService: PersonsServiceImpl

    @BeforeEach
    fun setUp() {
        personRepository = mock(PersonRepository::class.java)
        personEntityMapper = mock(PersonEntityMapper::class.java)
        personsService = PersonsServiceImpl(personRepository, personEntityMapper)
    }

    @Test
    fun getByIdShouldReturnPerson() {
        val id = 1L
        val personEntity = PersonEntity()
        val person = Person(id, "Anderson Oliveira")

        given(personRepository.findById(id)).willReturn(Optional.of(personEntity))
        given(personEntityMapper.toDto(personEntity)).willReturn(person)

        val result = personsService.getById(id)

        assertEquals(person, result)
        verify(personRepository).findById(id)
        verify(personEntityMapper).toDto(personEntity)
    }

    @Test
    fun getByIdShouldThrowsExceptionNotFound() {
        val id = 2L

        given(personRepository.findById(id)).willReturn(Optional.empty())

        val exception = assertThrows<PersonNotFoundException> {
            personsService.getById(id)
        }

        assertTrue(exception.message!!.contains(id.toString()))
        verify(personRepository).findById(id)
        verifyNoInteractions(personEntityMapper)
    }

    @Test
    fun savePersonShouldReturnPerson() {
        val person = Person(1L, "Anderson Oliveira")
        val personEntity = PersonEntity()
        val savedEntity = PersonEntity()

        given(personEntityMapper.toEntity(person)).willReturn(personEntity)
        given(personRepository.save(personEntity)).willReturn(savedEntity)
        given(personEntityMapper.toDto(savedEntity)).willReturn(person)

        val result = personsService.save(person)

        assertEquals(person, result)
        verify(personEntityMapper).toEntity(person)
        verify(personRepository).save(personEntity)
        verify(personEntityMapper).toDto(savedEntity)
    }

    @Test
    fun getAllShouldReturnsEmptyList() {
        given(personRepository.findAll()).willReturn(emptyList())

        val result = personsService.getAll()

        assertTrue(result.isEmpty())
        verify(personRepository).findAll()
        verifyNoInteractions(personEntityMapper)
    }

    @Test
    fun getAllShouldReturnMappedPersons() {
        val personEntity1 = PersonEntity()
        val personEntity2 = PersonEntity()
        val person1 = Person(1L, "A")
        val person2 = Person(2L, "B")

        given(personRepository.findAll()).willReturn(listOf(personEntity1, personEntity2))
        given(personEntityMapper.toDto(personEntity1)).willReturn(person1)
        given(personEntityMapper.toDto(personEntity2)).willReturn(person2)

        val result = personsService.getAll()

        assertEquals(listOf(person1, person2), result)
        verify(personRepository).findAll()
        verify(personEntityMapper).toDto(personEntity1)
        verify(personEntityMapper).toDto(personEntity2)
    }
}