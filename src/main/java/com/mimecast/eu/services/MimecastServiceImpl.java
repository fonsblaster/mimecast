package com.mimecast.eu.services;

import com.mimecast.eu.aspect.LogEverything;
import com.mimecast.eu.configuration.PropertiesBean;
import com.mimecast.eu.exception.MimecastException;
import com.mimecast.eu.model.Mimecast;
import com.mimecast.eu.model.Period;
import com.mimecast.eu.model.Rate;
import com.mimecast.eu.model.Rates;
import com.mimecast.eu.model.TopVat;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MimecastServiceImpl implements MimecastService {

    private final RestOperations restOperations;

    private final PropertiesBean propertiesBean;

    @Override
    @LogEverything
    public List<TopVat> getVatCountries(VatType vatType) throws RestClientException {
        log.info("Fetching json from server {}", propertiesBean.getMimecastUrl());
        Mimecast mimecast = Optional.ofNullable(restOperations.getForObject(propertiesBean.getMimecastUrl(), Mimecast.class))
                .orElseThrow(() -> new MimecastException("Cannot fetch mimecast Json from server."));

        Integer countriesToAnalyze = propertiesBean.getCountriesToAnalyze();

        if (mimecast.getRates().isEmpty() || mimecast.getRates().size() < countriesToAnalyze) {
            throw new MimecastException("Fetched mimecast contains less then " + countriesToAnalyze + " rates.");
        }

        log.info("Collecting countries with {} standard VAT.", vatType.name());
        return retrieveTopVats(mimecast).stream().sorted(compareVat(vatType)).limit(countriesToAnalyze).collect(Collectors.toList());
    }

    private List<TopVat> retrieveTopVats(Mimecast mimecast) {
        List<TopVat> topVat = new ArrayList<>();

        log.info("Filtering Rates from inactive periods.");
        for (Rate rate : mimecast.getRates()) {
            Rates rates = Collections.max(rate.getPeriods(), Comparator.comparing(Period::getEffectiveFrom)).getRates();
            topVat.add(new TopVat(rate.getName(), rates.getStandard()));
        }

        return topVat;
    }

    private Comparator<TopVat> compareVat(VatType vatType) {
        Comparator<TopVat> topVatComparator = Comparator.comparingDouble(TopVat::getStandardVat);
        return VatType.LOWEST.equals(vatType) ? topVatComparator : topVatComparator.reversed();
    }
}
