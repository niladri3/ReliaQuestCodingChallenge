package com.reliaquest.api.model.rqserver;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RQEmployeeDeleteRequest {
    private String name;
}
