package com.persons.finder.entity

import javax.persistence.*


@Entity
@Table(name = "person")
class PersonEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String = ""

) {
    constructor() : this(0, "")

}