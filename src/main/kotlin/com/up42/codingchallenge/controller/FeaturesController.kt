package com.up42.codingchallenge.controller

import com.up42.codingchallenge.model.FeatureCollection
import com.up42.codingchallenge.service.FeatureService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

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
        
}
