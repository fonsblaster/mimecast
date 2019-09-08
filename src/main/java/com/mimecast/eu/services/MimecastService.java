package com.mimecast.eu.services;

import com.mimecast.eu.model.TopVat;

import java.util.List;

public interface MimecastService {

    List<TopVat> getVatCountries(VatType vatType) throws Exception;

    enum VatType {
        HIGHEST,
        LOWEST
    }
}
