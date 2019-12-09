package com.element.hikers.backend.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAgeException extends RuntimeException {
    private Integer minAge;
    private Integer maxAge;
    private String email;


    public InvalidAgeException(Integer minAge, Integer maxAge, String email) {
        super();
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.email = email;
    }
}
