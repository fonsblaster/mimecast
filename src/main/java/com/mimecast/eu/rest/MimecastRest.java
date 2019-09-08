package com.mimecast.eu.rest;

import com.mimecast.eu.model.TopVat;
import com.mimecast.eu.services.MimecastService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mimecast.eu.services.MimecastService.VatType;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MimecastRest {

    private final MimecastService mimecastService;

    @RequestMapping(value = "/highestVatCountries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve the countries with the highest vat.")
    public List<TopVat> highestVatCountries() throws Exception {
        log.info("Requested list of countries with highest vat.");
        return mimecastService.getVatCountries(VatType.HIGHEST);
    }

    @RequestMapping(value = "/lowestVatCountries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve the countries with the lowest vat.")
    public List<TopVat> lowestVatCountries() throws Exception {
        log.info("Requested list of countries with lowest vat.");
        return mimecastService.getVatCountries(VatType.LOWEST);
    }
}