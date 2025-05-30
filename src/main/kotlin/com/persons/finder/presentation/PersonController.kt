package com.persons.finder.presentation

import com.persons.finder.constants.ApiDocs.DEFAULT_200_RESPONSE
import com.persons.finder.constants.ApiDocs.DEFAULT_201_RESPONSE
import com.persons.finder.constants.ApiDocs.DEFAULT_500_DESCRIPTION
import com.persons.finder.constants.ApiDocs.DEFAULT_500_RESPONSE
import com.persons.finder.constants.ApiDocs.PERSONS_CREATE
import com.persons.finder.constants.ApiDocs.PERSONS_CREATE_BODY_DESCRIPTION
import com.persons.finder.constants.ApiDocs.PERSONS_CREATE_DESCRIPTION
import com.persons.finder.constants.ApiDocs.PERSONS_CREATE_SUCCESS
import com.persons.finder.constants.ApiDocs.PERSONS_GET
import com.persons.finder.constants.ApiDocs.PERSONS_GET_DESCRIPTION
import com.persons.finder.constants.ApiDocs.PERSONS_GET_ID_DESCRIPTION
import com.persons.finder.constants.ApiDocs.PERSONS_GET_NEARBY
import com.persons.finder.constants.ApiDocs.PERSONS_GET_NEARBY_DESCRIPTION
import com.persons.finder.constants.ApiDocs.PERSONS_GET_NEARBY_LATITUDE
import com.persons.finder.constants.ApiDocs.PERSONS_GET_NEARBY_LONGITUDE
import com.persons.finder.constants.ApiDocs.PERSONS_GET_NEARBY_RADIUS_DESCRIPTION
import com.persons.finder.constants.ApiDocs.PERSONS_GET_NEARBY_SUCCESS
import com.persons.finder.constants.ApiDocs.PERSONS_GET_SUCCESS
import com.persons.finder.constants.ApiDocs.PERSON_CREATE_LOCATION
import com.persons.finder.constants.ApiDocs.PERSON_CREATE_LOCATION_BODY_DESCRIPTION
import com.persons.finder.constants.ApiDocs.PERSON_CREATE_LOCATION_DESCRIPTION
import com.persons.finder.constants.ApiDocs.PERSON_CREATE_LOCATION_ID_DESCRIPTION
import com.persons.finder.constants.ApiDocs.PERSON_CREATE_LOCATION_SUCCESS
import com.persons.finder.external.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping(
    value = ["api/v1/persons"],
    produces = [APPLICATION_JSON_VALUE]
)
interface PersonController {

    @Operation(
        summary = PERSON_CREATE_LOCATION,
        description = PERSON_CREATE_LOCATION_DESCRIPTION,
        parameters = [
            Parameter(
                description = PERSON_CREATE_LOCATION_ID_DESCRIPTION,
                name = "id",
                required = true,
                example = "1",
                `in` = ParameterIn.PATH,
            )
        ],
        requestBody = RequestBody(
            description = PERSON_CREATE_LOCATION_BODY_DESCRIPTION,
            required = true,
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ExtCreateLocation::class)
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = DEFAULT_201_RESPONSE,
                description = PERSON_CREATE_LOCATION_SUCCESS,
                content = [Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ExtLocation::class)
                )]
            ),
            ApiResponse(
                responseCode = DEFAULT_500_RESPONSE,
                description = DEFAULT_500_DESCRIPTION,
                content = [Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ExtRestApiError::class)
                )]
            )
        ]
    )
    fun saveLocation(extCreateLocation: ExtCreateLocation, id: String): ResponseEntity<ExtLocation>

    @Operation(
        summary = PERSONS_CREATE,
        description = PERSONS_CREATE_DESCRIPTION,
        requestBody = RequestBody(
            description = PERSONS_CREATE_BODY_DESCRIPTION,
            required = true,
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ExtCreatePerson::class)
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = DEFAULT_201_RESPONSE,
                description = PERSONS_CREATE_SUCCESS,
                content = [Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ExtPerson::class)
                )]
            ),
            ApiResponse(
                responseCode = DEFAULT_500_RESPONSE,
                description = DEFAULT_500_DESCRIPTION,
                content = [Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ExtRestApiError::class)
                )]
            )
        ]
    )
    fun createPerson(extCreatePerson: ExtCreatePerson): ResponseEntity<ExtPerson>

    @Operation(
        summary = PERSONS_GET_NEARBY,
        description = PERSONS_GET_NEARBY_DESCRIPTION,
        parameters = [
            Parameter(
                description = PERSONS_GET_NEARBY_LATITUDE,
                name = "lat",
                required = true,
                example = "100.123",
                `in` = ParameterIn.QUERY,
            ),
            Parameter(
                description = PERSONS_GET_NEARBY_LONGITUDE,
                name = "lon",
                required = true,
                example = "100.123",
                `in` = ParameterIn.QUERY,
            ),
            Parameter(
                description = PERSONS_GET_NEARBY_RADIUS_DESCRIPTION,
                name = "radiusKm",
                required = false,
                example = "10.0",
                `in` = ParameterIn.QUERY,
            )
        ],
        responses = [
            ApiResponse(
                responseCode = DEFAULT_200_RESPONSE,
                description = PERSONS_GET_NEARBY_SUCCESS,
                content = [Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ExtNearbyPeople::class)
                )]
            ),
            ApiResponse(
                responseCode = DEFAULT_500_RESPONSE,
                description = DEFAULT_500_DESCRIPTION,
                content = [Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ExtRestApiError::class)
                )]
            )
        ]
    )
    fun getNearbyPersons(latitude: Double, longitude: Double, radiusKm: Double): ResponseEntity<ExtNearbyPeople>


    @Operation(
        summary = PERSONS_GET,
        description = PERSONS_GET_DESCRIPTION,
        parameters = [
            Parameter(
                description = PERSONS_GET_ID_DESCRIPTION,
                name = "id",
                required = false,
                `in` = ParameterIn.QUERY,
            )
        ],
        responses = [
            ApiResponse(
                responseCode = DEFAULT_200_RESPONSE,
                description = PERSONS_GET_SUCCESS,
                content = [Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ExtPersons::class)
                )]
            ),
            ApiResponse(
                responseCode = DEFAULT_500_RESPONSE,
                description = DEFAULT_500_DESCRIPTION,
                content = [Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ExtRestApiError::class)
                )]
            )
        ]
    )
    fun getPersons(ids: List<Long>?): ResponseEntity<ExtPersons>


}