package com.mimecast.eu.configuration;

import com.mimecast.eu.services.MimecastService;
import com.mimecast.eu.services.MimecastServiceImpl;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

@Configuration
public class MimecastConfiguration {
    @Bean
    public PropertiesBean mimecastPropertiesBean() {
        return new PropertiesBean();
    }

    @Bean
    public RestOperations restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public MimecastService mimecastService(RestOperations restOperations, PropertiesBean propertiesBean) {
        return new MimecastServiceImpl(restOperations, propertiesBean);
    }
}
