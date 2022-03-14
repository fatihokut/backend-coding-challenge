package com.up42.codingchallenge.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class ImageCollection(var features: List<Feature>) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Feature(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        var properties: Properties? = null,
        var id: UUID?,
        var quicklook: String?
    ) {

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Properties(
            var id: UUID?,
            var quicklook: String?
        )
    }
}