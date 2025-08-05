package com.reliaquest.api.config;

import com.reliaquest.api.exception.ClientException;
import com.reliaquest.api.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class RestClientConfig {

    @Value("${employee.server.base-url}")
    private String baseUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
                    String message = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    log.error("Failure in processing the request. ErrorCode :{}, ErrorMessage :{}", response.getStatusCode(), message);
                    if (HttpStatus.TOO_MANY_REQUESTS.equals(response.getStatusCode())) {
                        throw new ClientException(response.getStatusCode(),
                                "Too many requests send to mock server. Please wait and try again.");
                    } else {
                        throw new ClientException(response.getStatusCode(), message);
                    }
                })
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (request, response) -> {
                    String message = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    log.error("Failure in processing the request by employee server. ErrorCode :{}, ErrorMessage :{}", response.getStatusCode(), message);
                    throw new ServerException(response.getStatusCode(), message);
                })
                .build();
    }
}
