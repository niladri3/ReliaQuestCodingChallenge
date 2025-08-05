package com.reliaquest.api.client;

import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.model.rqserver.RQEmployeeCreateRequest;
import com.reliaquest.api.model.rqserver.RQEmployeeDeleteRequest;

import java.util.List;

public interface RQEmployeeClient {

    List<EmployeeResponse> getEmployees();

    EmployeeResponse getEmployeeById(String id);

    String deleteEmployeeByName(RQEmployeeDeleteRequest rqEmployeeDeleteRequest);

    EmployeeResponse createEmployee(RQEmployeeCreateRequest rqEmployeeCreateRequest);
}
