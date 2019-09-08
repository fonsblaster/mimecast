package com.mimecast.eu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "details",
        "version",
        "rates"
})
public class Mimecast {
    @JsonProperty("details")
    private String details;

    @JsonProperty("version")
    private Object version;

    @JsonProperty("rates")
    private List<Rate> rates = new ArrayList<>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}