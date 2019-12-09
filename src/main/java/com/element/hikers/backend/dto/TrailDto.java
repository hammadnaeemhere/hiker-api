package com.element.hikers.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrailDto {
    private Long trailId;
    @NotNull(message = "{trail.name.not_null}")
    private String name;
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "{trail.start_time.pattern}")
    private String startTime;
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "{trail.end_time.pattern}")
    private String endTime;
    @NotNull(message = "{trail.min_age.not_null}")
    private Integer minAge;
    @NotNull(message = "{trail.max_age.not_null}")
    private Integer maxAge;
    @NotNull(message = "{trail.ticket_price.not_null}")
    private BigDecimal ticketPrice;
    @NotNull(message = "{trail.currency.not_null}")
    private String currency;
}
