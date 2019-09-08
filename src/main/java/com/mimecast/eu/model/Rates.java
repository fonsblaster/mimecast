package com.mimecast.eu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "super_reduced",
        "reduced",
        "standard",
        "reduced1",
        "reduced2",
        "parking"
})
public class Rates {
    @JsonProperty("super_reduced")
    public Double superReduced;

    @JsonProperty("reduced")
    public Double reduced;

    @JsonProperty("standard")
    public Double standard;

    @JsonProperty("reduced1")
    public Double reduced1;

    @JsonProperty("reduced2")
    public Double reduced2;

    @JsonProperty("parking")
    public Double parking;

    @JsonIgnore
    private Map<String, Object> additionalProperties;
}