package com.reliaquest.api.model.rqserver;

import com.reliaquest.api.model.EmployeeCreateRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RQEmployeeCreateRequest {

    private String name;
    private Integer salary;
    private Integer age;
    private String title;

    public static RQEmployeeCreateRequest from(EmployeeCreateRequest employeeCreateRequest){
        return RQEmployeeCreateRequest.builder()
                .name(employeeCreateRequest.getName())
                .salary(employeeCreateRequest.getSalary())
                .age(employeeCreateRequest.getAge())
                .title(employeeCreateRequest.getTitle())
                .build();
    }
}
