package com.reliaquest.api.service;

import com.reliaquest.api.client.RQEmployeeClient;
import com.reliaquest.api.exception.ClientException;
import com.reliaquest.api.model.EmployeeCreateRequest;
import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.model.rqserver.RQEmployeeCreateRequest;
import com.reliaquest.api.model.rqserver.RQEmployeeDeleteRequest;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    private final RQEmployeeClient rqEmployeeClient;

    @Autowired
    public EmployeeService(RQEmployeeClient rqEmployeeClient) {
        this.rqEmployeeClient = rqEmployeeClient;
    }

    public EmployeeResponse createEmployee(EmployeeCreateRequest employeeCreateRequest) {
        return rqEmployeeClient.createEmployee(RQEmployeeCreateRequest.from(employeeCreateRequest));
    }


    public List<EmployeeResponse> getAllEmployees() {
        return rqEmployeeClient.getEmployees();
    }

    public List<EmployeeResponse> getSearchableEmployees(String searchString) {
        String searchStringLowerCase = searchString.toLowerCase();
        return rqEmployeeClient.getEmployees().stream()
                .filter(employeeResponse -> StringUtils.isNotEmpty(employeeResponse.getEmployeeName()) &&
                        employeeResponse.getEmployeeName().toLowerCase().contains(searchStringLowerCase))
                .toList();
    }


    public EmployeeResponse getEmployeeById(String id) {
        return rqEmployeeClient.getEmployeeById(id);
    }

    public Integer getHighestEmployeeSalary() {
        Integer highestSalary = rqEmployeeClient.getEmployees().stream()
                .map(EmployeeResponse::getEmployeeSalary)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(0);
        log.info("Retrieved highest salary of employee :{}", highestSalary);
        return highestSalary;
    }

    public List<String> getTopTenHighestEarningEmployeeName() {
        List<EmployeeResponse> employees = rqEmployeeClient.getEmployees();
        return employees.stream()
                .filter(employeeDto -> Objects.nonNull(employeeDto.getEmployeeSalary()))  // safeguard against nulls
                .sorted(Comparator.comparingInt(EmployeeResponse::getEmployeeSalary).reversed())
                .limit(10)
                .map(EmployeeResponse::getEmployeeName)
                .collect(Collectors.toList());
    }


    public String deleteEmployee(String id) {
        UUID idFormat = convertToUuid(id);
        EmployeeResponse employeeResponse = rqEmployeeClient.getEmployees().stream()
                .filter(employeeDto -> Objects.equals(employeeDto.getId(), idFormat))
                .findFirst()
                .orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND, "No employee found with the id : " + id));

        if (StringUtils.isEmpty(employeeResponse.getEmployeeName())) {
            log.error("Employee name does not exists with given id : {}", id);
            throw new ClientException(HttpStatus.UNPROCESSABLE_ENTITY, "Employee name does not exists with given id : " + id);
        }

        return rqEmployeeClient.deleteEmployeeByName(RQEmployeeDeleteRequest.builder()
                .name(employeeResponse.getEmployeeName())
                .build());
    }

    private UUID convertToUuid(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ClientException(HttpStatus.BAD_REQUEST, "The provided ID ('" + id + "') is not a valid UUID.");
        }
    }

}
