package com.element.hikers.backend.dto;

import com.element.hikers.backend.validators.IsFutureDate;
import com.element.hikers.backend.validators.ValidTrailId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long bookingId;

    @NotNull(message = "{booking.booking_date.not_null}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @IsFutureDate
    private Date bookingDate;

    private String bookingKey;

    @NotNull(message = "{booking.trail_id.not_null}")
    @ValidTrailId
    private Long trailId;

    private TrailDto trail;

    @Valid
    @NotEmpty(message = "{booking.hikers.not_null}")
    private List<HikerDto> hikers;

    private BigDecimal totalAmount;
    private Boolean isCancelled;
}
