package com.reliaquest.api.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ErrorResponse {
    private String details;
    private LocalDate timestamp;
}
