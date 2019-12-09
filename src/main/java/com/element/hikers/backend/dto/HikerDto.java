package com.element.hikers.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HikerDto {
    private Long hikerId;

    @NotEmpty(message = "hiker.first_name.not_empty")
    @Size(min = 2, max = 255, message = "hiker.first_name.length")
    private String firstName;

    @NotEmpty(message = "hiker.last_name.not_empty")
    @Size(min = 2, max = 255, message = "hiker.last_name.length")
    private String lastName;

    @NotNull(message = "hiker.age.not_null")
    @Max(value = 110, message = "hiker.age.max_value")
    @Positive(message = "hiker.age.non_negative")
    private Integer age;

    @NotEmpty(message = "hiker.mobile_no.not_empty")
    @Size(min = 11, max = 11, message = "hiker.mobile_no.length")
    private String mobileNo;

    @NotEmpty(message = "hiker.email.not_empty")
    @Email(message = "hiker.email.pattern")
    @Size(min = 2, max = 255, message = "hiker.email.length")
    private String email;
}
