package com.reliaquest.api.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeCreateRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Salary must not be null")
    @Positive(message = "Salary must be a positive number")
    private Integer salary;

    @NotNull(message = "Age must not be null")
    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 75, message = "Age must not exceed 75")
    private Integer age;

    @NotBlank(message = "Title must not be blank")
    private String title;
}
