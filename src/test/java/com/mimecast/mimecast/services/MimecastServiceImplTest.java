package com.mimecast.mimecast.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimecast.eu.configuration.PropertiesBean;
import com.mimecast.eu.exception.MimecastException;
import com.mimecast.eu.model.Mimecast;
import com.mimecast.eu.model.TopVat;
import com.mimecast.eu.services.MimecastService;
import com.mimecast.eu.services.MimecastServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestOperations;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MimecastServiceImplTest {

    @Mock
    private RestOperations restOperations;

    @Mock
    private PropertiesBean propertiesBean;

    private MimecastService mimecastService;

    private  ObjectMapper jsonMapper;

    @Before
    public void initServices( ) {
       mimecastService = new MimecastServiceImpl(restOperations, propertiesBean);
       when(propertiesBean.getCountriesToAnalyze()).thenReturn(3);
       when(propertiesBean.getMimecastUrl()).thenReturn("http://mockedAddress.com");
      jsonMapper = new ObjectMapper();
    }

    @Test
    public void getHighestVatCountriesValidTest() throws Exception {
        File validJson = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("validJson.json")).getFile());
        Mimecast validMimecast = jsonMapper.readValue(validJson, Mimecast.class);

        when(restOperations.getForObject(anyString(), Mockito.any())).thenReturn(validMimecast);

        List<TopVat> vatCountries = mimecastService.getVatCountries(MimecastService.VatType.HIGHEST);

        assertEquals(vatCountries.size(), 3);
        assertEquals(vatCountries.get(0), new TopVat("Hungary", 27.00));
        assertEquals(vatCountries.get(1), new TopVat("Sweden", 25.00));
        assertEquals(vatCountries.get(2), new TopVat("Denmark", 25.00));
    }

    @Test
    public void getLowestVatCountriesValidTest() throws Exception {
        File validJson = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("validJson.json")).getFile());
        Mimecast mimecast = jsonMapper.readValue(validJson, Mimecast.class);

        when(restOperations.getForObject(anyString(), any())).thenReturn(mimecast);

        List<TopVat> vatCountries = mimecastService.getVatCountries(MimecastService.VatType.LOWEST);

        assertEquals(vatCountries.size(), 3);
        assertEquals(vatCountries.get(0), new TopVat("Luxembourg",17.00));
        assertEquals(vatCountries.get(1), new TopVat("Malta",18.00));
        assertEquals(vatCountries.get(2), new TopVat("Cyprus",19.00));
    }

    @Test(expected = NoSuchElementException.class)
    public void missingMimecastFieldsTest() throws Exception {
        File invalidJsonFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("invalidJson.json")).getFile());
        Mimecast mimecast = jsonMapper.readValue(invalidJsonFile, Mimecast.class);

        when(propertiesBean.getCountriesToAnalyze()).thenReturn(3);
        when(restOperations.getForObject(anyString(), any())).thenReturn(mimecast);

        mimecastService.getVatCountries(MimecastService.VatType.LOWEST);
    }

    @Test(expected = MimecastException.class)
    public void notEnoughCountriesToAnalyze() throws Exception {
        File validJson = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("validJson.json")).getFile());
        Mimecast mimecast = jsonMapper.readValue(validJson, Mimecast.class);

        when(propertiesBean.getCountriesToAnalyze()).thenReturn(300);
        when(restOperations.getForObject(anyString(), any())).thenReturn(mimecast);

        mimecastService.getVatCountries(MimecastService.VatType.LOWEST);
    }
}
