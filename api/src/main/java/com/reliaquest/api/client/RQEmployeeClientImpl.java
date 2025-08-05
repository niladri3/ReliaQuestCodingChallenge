package com.reliaquest.api.client;

import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.model.rqserver.RQEmployeeCreateRequest;
import com.reliaquest.api.model.rqserver.RQEmployeeDeleteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
@EnableRetry
public class RQEmployeeClientImpl implements RQEmployeeClient {

    private final RestClient restClient;

    @Autowired
    public RQEmployeeClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<EmployeeResponse> getEmployees() {
        return List.of();
    }

    @Override
    public EmployeeResponse getEmployeeById(String id) {
        return null;
    }

    @Override
    public String deleteEmployeeByName(RQEmployeeDeleteRequest rqEmployeeDeleteRequest) {
        return "";
    }

    @Override
    public EmployeeResponse createEmployee(RQEmployeeCreateRequest rqEmployeeCreateRequest) {
        return null;
    }
}
