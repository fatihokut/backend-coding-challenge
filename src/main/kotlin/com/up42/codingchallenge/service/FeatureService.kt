package com.up42.codingchallenge.service

import com.up42.codingchallenge.model.FeatureCollection
import com.up42.codingchallenge.model.ImageCollection

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream
import java.util.Base64
import java.util.UUID
import kotlin.text.toByteArray
import kotlin.io.byteInputStream
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class FeatureService {

    fun getFeatures(): List<FeatureCollection.Feature> =
        ClassPathResource("/static/source-data.json").file.readText()
            .let { jsonString ->
                jacksonObjectMapper().readValue<List<FeatureCollection>>(jsonString)
            }.flatMap {
                it.features
            }.map {
                it.apply {
                    id = properties?.id
                    timestamp = properties?.timestamp
                    beginViewingDate = properties?.acquisition?.beginViewingDate
                    endViewingDate = properties?.acquisition?.endViewingDate
                    missionName = properties?.acquisition?.missionName
                }
            }.ifEmpty {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "No features found")
            }

    fun getImageBytes(id: UUID): ByteArray? =
        ClassPathResource("/static/source-data.json").file.readText()
            .let { jsonString ->
                jacksonObjectMapper().readValue<List<ImageCollection>>(jsonString) 
            }.flatMap {
                it.features
            }.find {
                it.properties?.id == id
            }?.properties?.quicklook.let {
                if (it.isNullOrEmpty()) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No image found")
                Base64.getDecoder().decode(it)
            }
}