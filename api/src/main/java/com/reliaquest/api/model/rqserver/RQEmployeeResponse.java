package com.reliaquest.api.model.rqserver;

import lombok.Data;

@Data
public class RQEmployeeResponse<T> {

    private T data;
    private String status;
}
