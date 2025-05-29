package com.persons.finder.entity

import javax.persistence.*

@Entity
@Table(name = "location")
class LocationEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    var person: PersonEntity,

    var latitude: Double,
    var longitude: Double
) {
    constructor() : this(0, PersonEntity(), 0.0, 0.0)

    constructor(person: PersonEntity, latitude: Double, longitude: Double) : this(0, person, latitude, longitude)
}