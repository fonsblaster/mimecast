package com.mimecast.eu.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class PropertiesBean {
    @Value("${countries.to.analyze}")
    private Integer countriesToAnalyze;

    @Value("${mimecast.url}")
    private String mimecastUrl;
}
