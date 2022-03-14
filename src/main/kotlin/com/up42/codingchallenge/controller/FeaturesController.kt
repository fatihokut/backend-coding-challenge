package com.up42.codingchallenge.controller

import com.up42.codingchallenge.model.FeatureCollection
import com.up42.codingchallenge.service.FeatureService

import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

@RestController
class FeaturesController {

    @Autowired
    private lateinit var featureService: FeatureService

    @GetMapping("/features")
    fun getFeatures(): ResponseEntity<List<FeatureCollection.Feature>> {
        return try {
            val features = featureService.getFeatures()

            ResponseEntity.ok()
                .body(features)

            } catch(e: ResponseStatusException) {
                ResponseEntity
                    .notFound()
                    .build()
            }
    }

    @GetMapping("/features/{featureId}/quicklook")
    fun getImage(@PathVariable featureId: UUID): ResponseEntity<Any>? {
        
        return try {
            val imageBytes = featureService.getImageBytes(featureId)
      
            ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
                .body(imageBytes)
      
            } catch(e: NoSuchElementException) {
                ResponseEntity
                    .notFound()
                    .build()
            }
    }
        
}
