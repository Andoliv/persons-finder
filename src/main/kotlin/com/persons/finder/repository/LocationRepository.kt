package com.persons.finder.repository

import com.persons.finder.entity.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<LocationEntity, Long> {
    fun findByLatitudeBetweenAndLongitudeBetween(
        minLatitude: Double,
        maxLatitude: Double,
        minLongitude: Double,
        maxLongitude: Double
    ): List<LocationEntity>


}