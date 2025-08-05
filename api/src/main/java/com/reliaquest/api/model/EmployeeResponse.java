package com.reliaquest.api.model;

import com.reliaquest.api.model.rqserver.RQEmployeeData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class EmployeeResponse {

    private UUID id;
    private String employeeName;
    private Integer employeeSalary;
    private Integer employeeAge;
    private String employeeTitle;
    private String employeeEmail;

    public static List<EmployeeResponse> from(List<RQEmployeeData> rqEmployeeList) {
        return rqEmployeeList.stream()
                .map(rqEmployeeData -> EmployeeResponse.builder()
                        .id(rqEmployeeData.getId())
                        .employeeName(rqEmployeeData.getEmployee_name())
                        .employeeSalary(rqEmployeeData.getEmployee_salary())
                        .employeeAge(rqEmployeeData.getEmployee_age())
                        .employeeTitle(rqEmployeeData.getEmployee_title())
                        .employeeEmail(rqEmployeeData.getEmployee_email())
                        .build())
                .toList();
    }

    public static EmployeeResponse from(RQEmployeeData rqEmployeeData) {
        return EmployeeResponse.builder()
                .id(rqEmployeeData.getId())
                .employeeName(rqEmployeeData.getEmployee_name())
                .employeeSalary(rqEmployeeData.getEmployee_salary())
                .employeeAge(rqEmployeeData.getEmployee_age())
                .employeeTitle(rqEmployeeData.getEmployee_title())
                .employeeEmail(rqEmployeeData.getEmployee_email())
                .build();
    }

}
