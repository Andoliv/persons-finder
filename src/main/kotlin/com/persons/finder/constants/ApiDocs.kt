package com.persons.finder.constants

object ApiDocs {

    /**
     * Default
     */
    const val DEFAULT_200_RESPONSE = "200"
    const val DEFAULT_201_RESPONSE = "201"
    const val DEFAULT_500_RESPONSE = "500"
    const val DEFAULT_500_DESCRIPTION = "Internal Server Error"

    /**
     * Persons API
     */
    const val PERSONS_API_TITLE = "Persons Finder API"
    const val PERSONS_API_DESCRIPTION = "API for managing persons and their locations"

    const val PERSONS_GET = "Get all persons - By id query is optional"
    const val PERSONS_GET_DESCRIPTION = "Retrieve a list of all persons with optional filtering by ID"
    const val PERSONS_GET_ID_DESCRIPTION = "ID of the person to retrieve. If not provided, all persons will be returned"
    const val PERSONS_GET_SUCCESS = "Persons retrieved successfully"

    const val PERSONS_CREATE = "Create/Update a new person"
    const val PERSONS_CREATE_DESCRIPTION = "Create/Update a new person with the provided details"
    const val PERSONS_CREATE_BODY_DESCRIPTION = "Details of the person to be created or updated"
    const val PERSONS_CREATE_SUCCESS = "Person created or updated successfully"

    const val PERSON_CREATE_LOCATION = "Create/Update a person's location"
    const val PERSON_CREATE_LOCATION_DESCRIPTION = "Creates or updates a location for a person identified by their ID."
    const val PERSON_CREATE_LOCATION_BODY_DESCRIPTION = "Location details to be saved for the person."
    const val PERSON_CREATE_LOCATION_ID_DESCRIPTION = "ID of the person whose location is being saved"
    const val PERSON_CREATE_LOCATION_SUCCESS = "Person's location saved"

    const val PERSONS_GET_NEARBY = "Get nearby persons"
    const val PERSONS_GET_NEARBY_DESCRIPTION = "Retrieve persons near a specified location within a given radius"
    const val PERSONS_GET_NEARBY_LATITUDE = "Get nearby latitude near the radius"
    const val PERSONS_GET_NEARBY_LONGITUDE = "Get nearby longitude near the radius"
    const val PERSONS_GET_NEARBY_RADIUS_DESCRIPTION = "Radius in kilometers to search for nearby persons"
    const val PERSONS_GET_NEARBY_SUCCESS = "Nearby persons retrieved successfully"


}