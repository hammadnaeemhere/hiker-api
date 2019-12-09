package com.element.hikers.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {
    @Builder.Default
    private String message = "Success!";

    private T body;
}
