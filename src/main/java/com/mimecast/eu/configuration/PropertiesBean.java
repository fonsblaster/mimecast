package com.mimecast.eu.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class PropertiesBean {

    private Integer countriesToAnalyze;
    private String mimecastUrl;

    public Integer getCountriesToAnalyze() {
        return countriesToAnalyze;
    }

    public void setCountriesToAnalyze(Integer countriesToAnalyze) {
        this.countriesToAnalyze = countriesToAnalyze;
    }

    public String getMimecastUrl() {
        return mimecastUrl;
    }

    public void setMimecastUrl(String mimecastUrl) {
        this.mimecastUrl = mimecastUrl;
    }
}
