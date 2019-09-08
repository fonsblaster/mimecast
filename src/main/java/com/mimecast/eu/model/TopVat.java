package com.mimecast.eu.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopVat {
    private final String countryName;

    private final Double standardVat;
}