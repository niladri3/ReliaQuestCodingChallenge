package com.reliaquest.api.client;

import com.reliaquest.api.exception.ClientException;
import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.model.rqserver.RQEmployeeCreateRequest;
import com.reliaquest.api.model.rqserver.RQEmployeeData;
import com.reliaquest.api.model.rqserver.RQEmployeeDeleteRequest;
import com.reliaquest.api.model.rqserver.RQEmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@EnableRetry
public class RQEmployeeClientImpl implements RQEmployeeClient {

    private final RestClient restClient;

    @Autowired
    public RQEmployeeClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Retryable(
            retryFor = ResourceAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public List<EmployeeResponse> getEmployees() {
        RQEmployeeResponse<List<RQEmployeeData>> response = restClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        List<RQEmployeeData> rqEmployeeList = Optional.ofNullable(response)
                .map(RQEmployeeResponse::getData)
                .orElseGet(Collections::emptyList);

        log.info("Retrieved {} employees.", rqEmployeeList.size());
        return EmployeeResponse.from(rqEmployeeList);
    }


    @Retryable(
            retryFor = ResourceAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public EmployeeResponse getEmployeeById(String id) {
        RQEmployeeResponse<RQEmployeeData> response = restClient.get()
                .uri("/{id}", id)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        RQEmployeeData rqEmployeeData = Optional.ofNullable(response)
                .map(RQEmployeeResponse::getData)
                .orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND,
                        "Failed to get data for the given employee Id : " + id));

        log.info("Successfully fetched employee data: {} for given id: {}.", rqEmployeeData, id);
        return EmployeeResponse.from(rqEmployeeData);
    }


    @Retryable(
            retryFor = ResourceAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public String deleteEmployeeByName(RQEmployeeDeleteRequest rqEmployeeDeleteRequest) {

        RQEmployeeResponse<Boolean> response = restClient.method(HttpMethod.DELETE)
                .body(rqEmployeeDeleteRequest)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        boolean isDeleted = Optional.ofNullable(response)
                .map(RQEmployeeResponse::getData)
                .orElseThrow(() -> new ClientException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Failed to delete employee: missing or incomplete Employee API response."));

        // In most cases, `isDeleted` will not be false, because EmployeeService always validates the ID and name
        // before invoking this method. This check is purely defensive—to catch any unexpected conditions.
        if (!isDeleted) {
            throw new ClientException(HttpStatus.NOT_FOUND, "Data does not exist with given name : "
                    + rqEmployeeDeleteRequest.getName());
        }

        log.info("Successfully deleted employee: {}", rqEmployeeDeleteRequest.getName());
        return rqEmployeeDeleteRequest.getName();
    }


    @Retryable(
            retryFor = ResourceAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public EmployeeResponse createEmployee(RQEmployeeCreateRequest rqEmployeeCreateRequest) {
        RQEmployeeResponse<RQEmployeeData> response = restClient.post()
                .body(rqEmployeeCreateRequest)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        RQEmployeeData RQEmployeeData = Optional.ofNullable(response)
                .map(RQEmployeeResponse::getData)
                .orElseThrow(() -> new ClientException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Failed to create employee: missing or incomplete Employee API response."));

        log.info("Successfully created employee: {}", RQEmployeeData);
        return EmployeeResponse.from(RQEmployeeData);
    }
}
