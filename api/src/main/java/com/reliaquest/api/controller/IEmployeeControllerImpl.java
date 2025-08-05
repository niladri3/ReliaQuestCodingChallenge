package com.reliaquest.api.controller;

import com.reliaquest.api.model.EmployeeCreateRequest;
import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/employee")
public class IEmployeeControllerImpl implements IEmployeeController<EmployeeResponse, EmployeeCreateRequest> {

    private final EmployeeService employeeService;

    @Autowired
    public IEmployeeControllerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employeeResponse = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByNameSearch(String searchString) {
        return new ResponseEntity<>(employeeService.getSearchableEmployees(searchString), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployeeById(String id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return new ResponseEntity<>(employeeService.getHighestEmployeeSalary(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return new ResponseEntity<>(employeeService.getTopTenHighestEarningEmployeeName(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid EmployeeCreateRequest employeeInput) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeInput),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return new ResponseEntity<>(employeeService.deleteEmployee(id),HttpStatus.OK);
    }
}
