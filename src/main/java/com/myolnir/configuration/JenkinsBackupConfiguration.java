package com.myolnir.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Myolnir on 04/07/2017.
 */
@Configuration
public class JenkinsBackupConfiguration {

    @Value("${custom.rest.connection.connection-request-timeout}")
    private int connectionRequestTimeout;

    @Value("${custom.rest.connection.connect-timeout}")
    private int connectionConnectTimeout;

    @Value("${custom.rest.connection.read-timeout}")
    private int connectionReadTimeout;

    @Bean
    @ConfigurationProperties(prefix = "custom.rest.connection")
    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory()
    {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(connectionConnectTimeout);
        factory.setConnectionRequestTimeout(connectionRequestTimeout);
        factory.setReadTimeout(connectionReadTimeout);
        return factory;
    }

    @Bean
    public RestTemplate customRestTemplate()
    {
        return new RestTemplate(customHttpRequestFactory());
    }
}
