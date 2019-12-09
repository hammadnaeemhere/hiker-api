package com.element.hikers.backend.controller;

import com.element.hikers.backend.dto.BookingDto;
import com.element.hikers.backend.dto.ResponseDto;
import com.element.hikers.backend.service.BookingService;
import com.element.hikers.backend.validators.ValidBookingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@RestController
@Validated
@Slf4j
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<ResponseDto> create(@RequestBody @Valid BookingDto booking) {
        log.debug("executing creating booking: {}", booking);
        ResponseDto responseDTO = ResponseDto.builder()
                .message("Your booking has been successful. Please remember booking id and booking key in order to cancel the booking.")
                .body(bookingService.save(booking)).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @PutMapping("/bookings/{id}/cancel")
    public ResponseEntity<ResponseDto> cancelBooking(@PathVariable @ValidBookingId Long id, @RequestParam @NotEmpty String bookingKey) {
        log.debug("booking cancelled requested for  booking: {}", id);
        ResponseDto responseDTO = ResponseDto.builder()
                .message("Your booking has been cancelled.")
                .body(bookingService.cancel(id, bookingKey)).build();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/auth/bookings")
    public ResponseEntity<ResponseDto> getAll(
            @RequestParam(required = false) Long trailId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookingDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookingFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookingTo,
            @RequestParam(required = false) Boolean cancelled) {

        log.debug("get all booking called with trailId={}, bookingDate={}, bookingFrom={}, bookingTo={}, cancelled={} ", trailId, bookingDate, bookingFrom, bookingTo, cancelled);
        ResponseDto responseDTO = ResponseDto.builder()
                .body(bookingService.getAll(trailId, bookingDate, bookingFrom, bookingTo, cancelled)).build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
